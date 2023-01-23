package com.ebi.snap_food.security.service;


import com.ebi.snap_food.infra.service.BaseService;
import com.ebi.snap_food.security.dto.UsersDto;
import com.ebi.snap_food.security.model.Users;
import com.ebi.snap_food.security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsersService extends BaseService<Users, UsersDto,Long> {

//    @Autowired
//    private UserSpecification userSpecification;


    private Integer defaultPageSize=3;

    @Autowired
    UsersRepository repository;
    @Override
    public UsersRepository getRepository() {
        return repository;
    }

    @Override
    public Class<UsersDto> getDtoClazz() {
        return UsersDto.class;
    }

    @Override
    public Class<Users> getEntityClazz() {
        return Users.class;
    }

//    @Override
//    public UserDetails loadUserByUsername(String mobile_no) throws UsernameNotFoundException {
//        Users user = repository.getUserByPhoneNumber(mobile_no);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with username: " + user.getUserName());
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
//                new ArrayList<>());
//    }
    @Transactional
    public Users getUser(String userName){
       return repository.getUserByUsername(userName);
    }

    @Transactional
    public Users getUserByMobileNo(String userName){
        return repository.getUserByPhoneNumber(userName);
    }

    @Transactional
    public List<Users> findByName(String name){
        List<Users> results = repository.findByFullnameContaining(name);
        return results ;}

    @Transactional
    public List<Users> findByNationalCode(String name){
        List<Users> results = repository.findByNationalCodeContaining(name);
        return results ;}

//
//    @Transactional
//    public List<Users> getUsersByRoleAbbreviation(String roleAbbreviation){
//    List<Users> results = repository.getUsersByRoleAbbreviation( roleAbbreviation);
//        return results ;}
//
//    public ResponseList getUsers(UserSearchInfo searchInfo) {
//        List<Users> list = null;
//        Page<Users> pages = null;
//        if (searchInfo.getPageNumber() == null) {
//            pages = new PageImpl<>(repository.findAll(userSpecification.getUsers(searchInfo)));
//        } else {
//            if (searchInfo.getCount() == null)
//                searchInfo.setCount(defaultPageSize);
//            Pageable paging = PageRequest.of(searchInfo.getPageNumber() - 1, searchInfo.getCount());
//            pages = repository.findAll(userSpecification.getUsers(searchInfo), paging);
//        }
//        if (pages != null && pages.getContent() != null) {
//            list = pages.getContent();
//            if (list != null && list.size() > 0) {
//                ResponseList responseList = new ResponseList();
//                responseList.setTotalPage(pages.getTotalPages());
//                responseList.setTotalCount(pages.getTotalElements());
//                responseList.setPageNo(pages.getNumber() + 1);
//                responseList.setResult(new ArrayList<>());
//                for (Users users : list) {
//                    responseList.getResult().add(users.convert(UsersDto.class));
//                }
//                return responseList;
//            }
//        }
//        return null;
//    }

}
