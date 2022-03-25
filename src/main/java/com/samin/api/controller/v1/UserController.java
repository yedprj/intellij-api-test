package com.samin.api.controller.v1;

import com.samin.api.Service.ResponseService;
import com.samin.api.advice.exception.CUserNotFoundException;
import com.samin.api.entity.User;
import com.samin.api.model.response.CommonResult;
import com.samin.api.model.response.ListResult;
import com.samin.api.model.response.SingleResult;
import com.samin.api.repository.UserJpaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.lang.Exception;


import java.util.List;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserJpaRepository userJpaRepository;
    private final ResponseService responseService;          // 결과를 처리할 Service

    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
    @GetMapping(value = "/user")
    public ListResult<User> findAllUser() {
        // 결과데이터가 여러건인 경우 getListResult를 이용해서 결과를 출력
        return responseService.getListResult(userJpaRepository.findAll());
    }

    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
    @GetMapping(value = "/user/{userId}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원ID", required = true) @PathVariable int userId,
                                           @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        //결과 데이터가 단일건일 경우 getBasicResult를 이용해서 결과를 출력
        return responseService.getSingleResult(userJpaRepository.findById((long) userId).orElseThrow(CUserNotFoundException::new));
    }

    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다")
    @PostMapping(value = "/user")
    public SingleResult<User> save(@ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
                                   @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();

        return responseService.getSingleResult(userJpaRepository.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam long msrl,
            @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name) {

        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();

        return responseService.getSingleResult(userJpaRepository.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
        userJpaRepository.deleteById(msrl);
        //성공 결과 정보만 필요한 경우 getSuccessResult()를 이요하여 결과를 출력
        return responseService.getSuccessResult();
    }
}
