package com.ebi.snap_food.security.service;



import com.ebi.snap_food.infra.dto.CriminalUserSms;
import com.ebi.snap_food.security.dto.UsersDto;
import com.ebi.snap_food.security.model.Users;
import com.ebi.snap_food.security.service.UsersService;
import com.ebi.snap_food.utils.ErrorMessage;
import com.ebi.snap_food.utils.MessageWithId;
import com.ebi.snap_food.utils.SecurityUtils;
import com.ebi.snap_food.utils.sms.SendSmsResponsec;
import ir.iixgateway.iixswitch.atlas.UserAuthenticationService;
import ir.iixgateway.iixswitch.atlas.model.UserAuthenticationRequest;
import ir.iixgateway.iixswitch.atlas.model.UserAuthenticationResponse;
import ir.iixgateway.iixswitch.sms.SmsProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersService userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	SmsProviderService smsProviderService;

	@Value("${shahkar.control.active}")
	private String shahkarActive;

	@Autowired
	UserAuthenticationService userAuthenticationService;


	@Value("${sms.sending.active}")
	private String smsActive;

	@Value("${sms.username}")
	private String username;

	@Value("${sms.password}")
	private String password;

	@Value("${sms.from}")
	private String from;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userDao.getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new User(user.getUserName(), user.getActivitycode(),
				new ArrayList<>());
	}

	public UsersDto save(UsersDto usersDto) {
		//	Users newUser = new Users();
		//	newUser.setUserName(user.getUserName());
		usersDto.setActivitycode(bcryptEncoder.encode(usersDto.getActivitycode()));
		return userDao.save(usersDto);
	}

	public ResponseEntity<Object> update(UsersDto usersDto) {
		/////////////////////////////////////////////////////////////////
		try {
			Users olduser = null;
			olduser = userDao.getUser(usersDto.getUserName());
			if (shahkarActive.equals("true")) {
				ErrorMessage errorMessage = checkShahkar(usersDto, olduser);
				if (errorMessage.getStatus() != 200)
					return ResponseEntity.badRequest().headers
									(new HttpHeaders()).
							body(errorMessage);
			}
			System.out.println("create activitycode1");
			usersDto.setActivitycode("11111");
			//until send sms lunch unfortunately activity code have static value
			System.out.println("sms is active:" + smsActive);
			if (smsActive.equals("true")) {
				/////////////////////////////////////send sms with okhttp///////////////////////////////////

				///////////////////////////sms melipayamak without our panel///////////////////////////////
				String[] setto = {usersDto.getUserName()};

				SecurityUtils securityUtils = new SecurityUtils();
				(usersDto).setActivitycode(securityUtils.createSMSCode());//به درخواست دکتر غیر فعال شده برای اینکه 5 تا 1 را بفرستذ
				SendSmsResponsec sendSmsResponse = null;


				System.out.println("create activitycode1");
			/*ir.iixgateway.iixswitch.sms.model.SendSmsRequest sendSmsRequest1=new ir.iixgateway.iixswitch.sms.model.SendSmsRequest();

			sendSmsRequest1.setUsername(username);
			sendSmsRequest1.setPassword(password);
			sendSmsRequest1.setFrom(from);
			sendSmsRequest1.setTo(setto);
			sendSmsRequest1.setText("کد فعالسازی بیمانی: " + usersDto.getActivitycode());
			ir.iixgateway.iixswitch.sms.model.SendSmsResponse sendSmsResponse2 = smsProviderService.sendSms(sendSmsRequest1);//به درخواست دکتر غیر فعال شده برای اینکه 5 تا 1 را بفرستذ
			if (sendSmsResponse2.getValue().equals("11") || sendSmsResponse2.getRetStatus() !=1){
				usersDto.setActivitycode("54321");
			}*/
				///////////////////////////////////////////////////////////////////////////////////////////////////
				ir.iixgateway.iixswitch.sms.model.SendSmsRequestShared sendSmsRequest1 = new ir.iixgateway.iixswitch.sms.model.SendSmsRequestShared();

				sendSmsRequest1.setUsername(username);
				sendSmsRequest1.setPassword(password);
				sendSmsRequest1.setBodyId(98299);
				sendSmsRequest1.setTo(usersDto.getUserName());
				String text = " سامانه بیمانی ; شما ";
				sendSmsRequest1.setText(text + usersDto.getActivitycode());
				ir.iixgateway.iixswitch.sms.model.SendSmsResponse sendSmsResponse2 = smsProviderService.sendSmsShared(sendSmsRequest1);//به درخواست دکتر غیر فعال شده برای اینکه 5 تا 1 را بفرستذ
				if (sendSmsResponse2.getValue().equals("11") || sendSmsResponse2.getRetStatus() != 1) {
					System.out.println("send sms unsucces");
					usersDto.setActivitycode("54321");
				}
				System.out.println("create activitycode1");
				/////////////////////////////////////////////////////////////////////////////////////////

				;
			}
			/////////////////////////////////////////////////////////////////
			//Users olduser = new Users();
			//	olduser = userDao.getUser(usersDto.getUserName());
			usersDto.setActivitycode(bcryptEncoder.encode(usersDto.getActivitycode()));
			usersDto.setPassword(bcryptEncoder.encode(usersDto.getActivitycode()));
			if (olduser == null || olduser.getMobileNo().isEmpty()) {//کاربر اگر وجود ندارد به عنوان کاربر میهمان درج شود
			/*if (userDao.findByNationalCode(usersDto.getNationalCode()).size()!=0)
				return ResponseEntity.badRequest().headers
						(new HttpHeaders()).
						body(new ErrorMessage(400,"کد ملی قبلا برای کاربر دیگری ثبت شده "));*/
				usersDto.setFullname("کاربر میهمان");
				usersDto.setGuest(true);
				usersDto.setMobileNo(usersDto.getUserName());
				//usersDto.setNationalCode()
				usersDto = userDao.save(usersDto);


			} else {
				olduser.setActivitycode(usersDto.getActivitycode());
				olduser.setPassword(usersDto.getActivitycode());

			/*usersDto.setId(olduser.getId());
			usersDto.setMobile_no(olduser.getMobile_no());
			usersDto.setFullname(olduser.getFullname());*/
				//	usersDto.setPassword(olduser.getPassword());
				userDao.update(olduser.convert(UsersDto.class));
			}

			//	newUser.setUserName(user.getUserName());


			return ResponseEntity.ok().headers
							(new HttpHeaders()).
					body(new ErrorMessage(200, "کد فعالسازی برای شما ارسال  شد   "));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().headers
							(new HttpHeaders()).
					body(new ErrorMessage(400, "خطا در بازیابی    "));
		}


	}

	public ErrorMessage checkShahkar(UsersDto usersDto, Users oldUser) {
		try {
			if (usersDto.getDeviceType() != null) {

//اگر کاربر جدید است کنترل انجام شود
				if (shahkarActive.equals("true") & usersDto.getDeviceType().equals("app") & oldUser == null) {
					if (usersDto.getNationalCode() == null & usersDto.getBirthDate() == null)
						return new ErrorMessage(400, "ورود کد ملی و تاریخ تولد اجباری است ");

					UserAuthenticationRequest userAuthenticationRequest =
							new UserAuthenticationRequest(usersDto.getNationalCode(), usersDto.getUserName(), usersDto.getBirthDate().toString());
					UserAuthenticationResponse userAuthenticationResponse = userAuthenticationService.userAuthentication(userAuthenticationRequest);
					if (userAuthenticationResponse.isSucceed() == false) {

						return new ErrorMessage(400, userAuthenticationResponse.getMessage());

					} else {
						return new ErrorMessage(200, userAuthenticationResponse.getMessage());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorMessage(400, "خطا در ارتباط با مرکز  ");
		}

		return new ErrorMessage(200, "");

	}

	//////////////////////////////////for panel create user////////////////////////////
	public ResponseEntity<Object> updateUserPanel(UsersDto usersDto) {
		/////////////////////////////////////////////////////////////////
		try {
			UsersDto olduser = null;
			if (usersDto.getId()!=null) {//old user
				olduser = userDao.findById(usersDto.getId());
				if (olduser == null)
					return ResponseEntity.badRequest().headers
									(new HttpHeaders()).
							body(new ErrorMessage(400, "کاربر یافت نشد   "));
//کنترل تکراری بودن نام کاربری
				Users resultUser  = userDao.getUser(usersDto.getUserName());
				if (resultUser!=null){
					if (!resultUser.getId().equals(olduser.getId()))

						return ResponseEntity.badRequest().headers
										(new HttpHeaders()).
								body(new ErrorMessage(400, "نام کاربری قبلا تعریف شده    "));}
//کنترل تکراری بودن موبایل
				Users resultUser2  = userDao.getUserByMobileNo(usersDto.getMobileNo());
				if (resultUser2!=null){
					if (!resultUser2.getId().equals(olduser.getId()))

						return ResponseEntity.badRequest().headers
										(new HttpHeaders()).
								body(new ErrorMessage(400, "موبایل برای کاربر دیگری  قبلا تعریف شده    "));}

			}else {//new user

				olduser = new UsersDto();
				//
				Users resultUser  = userDao.getUser(usersDto.getUserName());
				if (resultUser!=null)

					return ResponseEntity.badRequest().headers
									(new HttpHeaders()).
							body(new ErrorMessage(400, "نام کاربری قبلا تعریف شده    "));


				Users resultUser2  = userDao.getUserByMobileNo(usersDto.getMobileNo());
				if (resultUser2!=null)

					return ResponseEntity.badRequest().headers
									(new HttpHeaders()).
							body(new ErrorMessage(400, "شماره موبایل برای کاربر دیگری  قبلا تعریف شده    "));

			}

			olduser.setActivitycode(bcryptEncoder.encode(usersDto.getPassword()));
			olduser.setPassword(olduser.getActivitycode());
			olduser.setFullname(usersDto.getFullname());
			olduser.setGuest(false);
			olduser.setMobileNo(usersDto.getMobileNo());
			olduser.setUserName(usersDto.getUserName());
			usersDto = userDao.save(olduser);


			return ResponseEntity.ok().headers
							(new HttpHeaders()).
					body(new MessageWithId(200, "مشخصات کاربر ایجاد و یا بروزرسانی شد    ", usersDto.getId()));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().headers
							(new HttpHeaders()).
					body(new ErrorMessage(400, "خطا در بازیابی    "));
		}


	}
	public ResponseEntity<Object> updateCriminalUser(CriminalUserSms criminalUserSms) {
		/////////////////////////////////////////////////////////////////
		try {

			Users olduser = null;
			olduser = userDao.getUser(criminalUserSms.getUserName());
			UsersDto usersDto=new UsersDto();

			System.out.println("create activitycode1");
			usersDto.setActivitycode("11111");
			System.out.println("sms is active:" + smsActive);
			if (smsActive.equals("true")) {
				String[] setto = {usersDto.getUserName()};

				SecurityUtils securityUtils = new SecurityUtils();
				(usersDto).setActivitycode(securityUtils.createSMSCode());//به درخواست دکتر غیر فعال شده برای اینکه 5 تا 1 را بفرستذ
				SendSmsResponsec sendSmsResponse = null;


				System.out.println("create activitycode1");

				///////////////////////////////////////////////////////////////////////////////////////////////////
				ir.iixgateway.iixswitch.sms.model.SendSmsRequestShared sendSmsRequest1 = new ir.iixgateway.iixswitch.sms.model.SendSmsRequestShared();

				sendSmsRequest1.setUsername(username);
				sendSmsRequest1.setPassword(password);
				sendSmsRequest1.setBodyId(117917);
				sendSmsRequest1.setTo(criminalUserSms.getUserName());
				String text = criminalUserSms.getPelak() +" ; "+criminalUserSms.getPolicyNo()+" ; " +usersDto.getActivitycode();
				sendSmsRequest1.setText(text );
				ir.iixgateway.iixswitch.sms.model.SendSmsResponse sendSmsResponse2 = smsProviderService.sendSmsShared(sendSmsRequest1);//به درخواست دکتر غیر فعال شده برای اینکه 5 تا 1 را بفرستذ
				if (sendSmsResponse2.getValue().equals("11") || sendSmsResponse2.getRetStatus() != 1) {
					System.out.println("send sms unsucces");
					usersDto.setActivitycode("114834");
				}
				System.out.println("create activitycode1");

			}
			/////////////////////////////////////////////////////////////////
			//Users olduser = new Users();
			//	olduser = userDao.getUser(usersDto.getUserName());
			usersDto.setActivitycode(bcryptEncoder.encode(usersDto.getActivitycode()));
			usersDto.setPassword(bcryptEncoder.encode(usersDto.getActivitycode()));
			if (olduser == null || olduser.getMobileNo().isEmpty()) {//کاربر اگر وجود ندارد به عنوان کاربر میهمان درج شود

				usersDto.setFullname("کاربر میهمان");
				usersDto.setGuest(true);
				usersDto.setMobileNo(criminalUserSms.getUserName());
				usersDto.setUserName(criminalUserSms.getUserName());
				//usersDto.setNationalCode()
				usersDto = userDao.save(usersDto);


			} else {
				olduser.setActivitycode(usersDto.getActivitycode());
				olduser.setPassword(usersDto.getActivitycode());

				userDao.update(olduser.convert(UsersDto.class));
			}
			return ResponseEntity.ok().headers
							(new HttpHeaders()).
					body(new ErrorMessage(200, "کد فعالسازی برای مقصر ارسال  شد   "));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().headers
							(new HttpHeaders()).
					body(new ErrorMessage(400, "خطا در بازیابی    "));
		}


	}
	////////////////////create user for composite panel user////////////////////
	/*public UsersDto createUserPanelWithoutResponse(UsersDto usersDto) {
		/////////////////////////////////////////////////////////////////
		try {
			UsersDto olduser = null;
			if (usersDto.getId()!=null) {//old user
				olduser = userDao.findById(usersDto.getId());
				if (olduser == null)
					return ResponseEntity.badRequest().headers
							(new HttpHeaders()).
							body(new ErrorMessage(400, "کاربر یافت نشد   "));
//کنترل تکراری بودن نام کاربری
				Users resultUser  = userDao.getUser(usersDto.getUserName());
				if (resultUser!=null){
					if (!resultUser.getId().equals(olduser.getId()))

						return ResponseEntity.badRequest().headers
								(new HttpHeaders()).
								body(new ErrorMessage(400, "نام کاربری قبلا تعریف شده    "));}
//کنترل تکراری بودن موبایل
				Users resultUser2  = userDao.getUserByMobileNo(usersDto.getMobileNo());
				if (resultUser2!=null){
					if (!resultUser2.getId().equals(olduser.getId()))

						return ResponseEntity.badRequest().headers
								(new HttpHeaders()).
								body(new ErrorMessage(400, "موبایل برای کاربر دیگری  قبلا تعریف شده    "));}

			}else {//new user

				olduser = new UsersDto();
				//
				Users resultUser  = userDao.getUser(usersDto.getUserName());
				if (resultUser!=null)

					return ResponseEntity.badRequest().headers
							(new HttpHeaders()).
							body(new ErrorMessage(400, "نام کاربری قبلا تعریف شده    "));


				Users resultUser2  = userDao.getUserByMobileNo(usersDto.getMobileNo());
				if (resultUser2!=null)

					return ResponseEntity.badRequest().headers
							(new HttpHeaders()).
							body(new ErrorMessage(400, "شماره موبایل برای کاربر دیگری  قبلا تعریف شده    "));

			}

			olduser.setActivitycode(bcryptEncoder.encode(usersDto.getPassword()));
			olduser.setPassword(olduser.getActivitycode());
			olduser.setFullname(usersDto.getFullname());
			olduser.setGuest(false);
			olduser.setMobileNo(usersDto.getMobileNo());
			olduser.setUserName(usersDto.getUserName());
			usersDto = userDao.save(olduser);


			return ResponseEntity.ok().headers
					(new HttpHeaders()).
					body(new MessageWithId(200, "مشخصات کاربر ایجاد و یا بروزرسانی شد    ", usersDto.getId()));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().headers
					(new HttpHeaders()).
					body(new ErrorMessage(400, "خطا در بازیابی    "));
		}


	}*/

	public ResponseEntity<Object> getCodeCntr(UsersDto usersDto) {
		/////////////////////////////////////////////////////////////////
		try {
			Users olduser = null;
			olduser = userDao.getUser(usersDto.getUserName());

			System.out.println("create activitycode1");
			usersDto.setActivitycode("11111");
			//until send sms lunch unfortunately activity code have static value
			System.out.println("sms is active:" + smsActive);
			if (smsActive.equals("true")) {
				/////////////////////////////////////send sms with okhttp///////////////////////////////////

				///////////////////////////sms melipayamak without our panel///////////////////////////////
				String[] setto = {usersDto.getUserName()};

				SecurityUtils securityUtils = new SecurityUtils();
				(usersDto).setActivitycode(securityUtils.createSMSCode());//به درخواست دکتر غیر فعال شده برای اینکه 5 تا 1 را بفرستذ
				SendSmsResponsec sendSmsResponse = null;


				System.out.println("create activitycode1");

				///////////////////////////////////////////////////////////////////////////////////////////////////
				ir.iixgateway.iixswitch.sms.model.SendSmsRequestShared sendSmsRequest1 = new ir.iixgateway.iixswitch.sms.model.SendSmsRequestShared();

				sendSmsRequest1.setUsername(username);
				sendSmsRequest1.setPassword(password);
				sendSmsRequest1.setBodyId(98299);
				sendSmsRequest1.setTo(usersDto.getUserName());
				String text = " سامانه بیمانی ; شما ";
				sendSmsRequest1.setText(text + usersDto.getActivitycode());
				ir.iixgateway.iixswitch.sms.model.SendSmsResponse sendSmsResponse2 = smsProviderService.sendSmsShared(sendSmsRequest1);//به درخواست دکتر غیر فعال شده برای اینکه 5 تا 1 را بفرستذ
				if (sendSmsResponse2.getValue().equals("11") || sendSmsResponse2.getRetStatus() != 1) {
					System.out.println("send sms unsucces");
					usersDto.setActivitycode("54321");
				}
				System.out.println("create activitycode1");
				/////////////////////////////////////////////////////////////////////////////////////////

				;
			}
			/////////////////////////////////////////////////////////////////
			//Users olduser = new Users();
			//	olduser = userDao.getUser(usersDto.getUserName());
			String tempActivityCod=usersDto.getActivitycode();
			usersDto.setActivitycode(bcryptEncoder.encode(usersDto.getActivitycode()));
			usersDto.setPassword(bcryptEncoder.encode(usersDto.getActivitycode()));
			if (olduser == null || olduser.getMobileNo().isEmpty()) {//کاربر اگر وجود ندارد به عنوان کاربر میهمان درج شود
			/*if (userDao.findByNationalCode(usersDto.getNationalCode()).size()!=0)
				return ResponseEntity.badRequest().headers
						(new HttpHeaders()).
						body(new ErrorMessage(400,"کد ملی قبلا برای کاربر دیگری ثبت شده "));*/
				usersDto.setFullname("کاربر میهمان");
				usersDto.setGuest(true);
				usersDto.setMobileNo(usersDto.getUserName());
				//usersDto.setNationalCode()
				usersDto = userDao.save(usersDto);


			} else {
				olduser.setActivitycode(usersDto.getActivitycode());
				olduser.setPassword(usersDto.getActivitycode());

			/*usersDto.setId(olduser.getId());
			usersDto.setMobile_no(olduser.getMobile_no());
			usersDto.setFullname(olduser.getFullname());*/
				//	usersDto.setPassword(olduser.getPassword());
				userDao.update(olduser.convert(UsersDto.class));
			}

			//	newUser.setUserName(user.getUserName());


			return ResponseEntity.ok().headers
							(new HttpHeaders()).
					body(new ErrorMessage(200, tempActivityCod));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().headers
							(new HttpHeaders()).
					body(new ErrorMessage(400, "خطا در بازیابی    "));
		}


	}
}