package KDT.Web_IDE.board.service;

import static KDT.Web_IDE.board.constant.BoardTestConstant.ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.board.entity.BoardUser;
import KDT.Web_IDE.domain.board.entity.repositroy.BoardUserRepository;
import KDT.Web_IDE.domain.board.service.BoardUserQueryService;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.BoardException;
import KDT.Web_IDE.global.exception.custom.MemberException;

@ActiveProfiles("dev")
public class BoardUserQueryServiceTest {

  @Mock private BoardUserRepository boardUserRepository;

  @InjectMocks private BoardUserQueryService boardUserQueryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Nested
  @DisplayName("BoardUser 객체를")
  class isLeader {

    BoardUser mockBoardUser = mock(BoardUser.class);

    @Test
    @DisplayName("조회하고 확인한다.")
    void isLeaderSuccess() {
      // give
      when(boardUserRepository.findByMember_IdAndBoard_Id(ID.getValue(), ID.getValue()))
          .thenReturn(Optional.of(mockBoardUser));
      when(mockBoardUser.getIsLeader()).thenReturn(true);

      // when
      boardUserQueryService.isLeader(ID.getValue(), ID.getValue());

      // then
      verify(boardUserRepository, times(1))
          .findByMember_IdAndBoard_Id(ID.getValue(), ID.getValue());
    }

    @Test
    @DisplayName("조회하고 없으면 예외를 발생시킨다")
    void isLeaderNotFoundThrowException() {
      // give
      when(boardUserRepository.findByMember_IdAndBoard_Id(ID.getValue(), ID.getValue()))
          .thenReturn(Optional.empty());

      // when
      MemberException memberException =
          assertThrows(
              MemberException.class,
              () -> boardUserQueryService.isLeader(ID.getValue(), ID.getValue()));

      // then
      assertEquals(memberException.getErrorCode(), GlobalErrorCode.NOT_IN_BOARD);
    }

    @Test
    @DisplayName("방장이 아니면 예외를 발생시킨다.")
    void isLeaderNotLeaderThrowException() {
      // give
      when(boardUserRepository.findByMember_IdAndBoard_Id(ID.getValue(), ID.getValue()))
          .thenReturn(Optional.of(mockBoardUser));
      when(mockBoardUser.getIsLeader()).thenReturn(false);

      // when
      BoardException boardException =
          assertThrows(
              BoardException.class,
              () -> boardUserQueryService.isLeader(ID.getValue(), ID.getValue()));

      // then
      assertEquals(boardException.getErrorCode(), GlobalErrorCode.NOT_LEADER);
    }
  }
}
