package KDT.Web_IDE.board.service;

import static KDT.Web_IDE.board.constant.BoardTestConstant.ISLEADER;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.board.entity.Board;
import KDT.Web_IDE.domain.board.entity.BoardUser;
import KDT.Web_IDE.domain.board.entity.repositroy.BoardUserRepository;
import KDT.Web_IDE.domain.board.service.BoardUserCommandService;
import KDT.Web_IDE.domain.member.entity.Member;

@ActiveProfiles("dev")
public class BoardUserCommandServiceTest {

  @Mock private BoardUserRepository boardUserRepository;

  @InjectMocks private BoardUserCommandService boardUserCommandService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("BoardUser를 생성 후 저장한다.")
  void createBoardUserSuccess() {
    // give
    Board mockBoard = mock(Board.class);
    Member mockMember = mock(Member.class);

    // when
    boardUserCommandService.createBoardUser(mockBoard, mockMember, ISLEADER.getValue());

    // then
    verify(boardUserRepository, times(1)).save(any(BoardUser.class));
  }
}
