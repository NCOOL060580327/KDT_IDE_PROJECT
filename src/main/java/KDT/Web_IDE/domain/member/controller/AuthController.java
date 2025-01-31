package KDT.Web_IDE.domain.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import KDT.Web_IDE.domain.member.dto.request.SignUpMemberRequestDto;
import KDT.Web_IDE.domain.member.service.auth.facade.AuthFacade;
import KDT.Web_IDE.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthFacade authFacade;

  @PostMapping("/sign")
  public BaseResponse<Void> signUpMember(@RequestBody SignUpMemberRequestDto requestDto) {
    authFacade.signUpMember(requestDto);
    return BaseResponse.onSuccess(null);
  }
}
