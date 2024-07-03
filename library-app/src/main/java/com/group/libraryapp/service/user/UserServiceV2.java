package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //아래 있는 함수가 시작될때 start transaction; 을 해준다(트랜젝션을 시작)
    //함수가 예외없이 끝났다면 commit
    //혹시라도 문제가 있다면 rollback
    @Transactional
    //JPA를 통한 CRUD 구현
    public void saveUser(UserCreateRequest request){
        //내장되어 있는 save함수로 insert구문 실현
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request){
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
        userRepository.save(user);  //transaction의 영속성 컨텍스트때문에 주석처리해줘도 됨.
    }

    public void deleteUser(String name){
        //SELECT*FROM user WHERE name = ?
        //findById내장 함수는 있지만 name함수는 없기에 UserRepository Interface에 함수 정의
        User user = userRepository.findByName(name);
        if(user == null){
            throw new IllegalArgumentException();
        }

        userRepository.delete(user);
    }
}
