package KDT.Web_IDE.board.service;

import static KDT.Web_IDE.board.constant.BoardTestConstant.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.board.entity.Board;
import KDT.Web_IDE.domain.board.entity.repositroy.BoardRepository;
import KDT.Web_IDE.domain.board.service.BoardQueryService;
import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.BoardException;

@ActiveProfiles("dev")
public class BoardQueryServiceTest {

  @Mock private BoardRepository boardRepository;

  @InjectMocks private BoardQueryService boardQueryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Nested
  @DisplayName("게시판을")
  class board {

    @Test
    @DisplayName("아이디를 통해 조회한다.")
    void getBoardByBoardIdSuccess() {
      // give
      Board mockBoard = mock(Board.class);
      when(boardRepository.findById(ID.getValue())).thenReturn(Optional.of(mockBoard));

      // when
      Board board = boardQueryService.getBoardByBoardId(ID.getValue());

      // then
      assertEquals(mockBoard, board);
    }

    @Test
    @DisplayName("존재하지 않으면 예외를 발생시킨다.")
    void getBoardByIdThrowException() {
      // give
      when(boardRepository.findById(ID.getValue())).thenReturn(Optional.empty());

      // when
      BoardException boardException =
          assertThrows(
              BoardException.class, () -> boardQueryService.getBoardByBoardId(ID.getValue()));

      // then
      assertEquals(boardException.getErrorCode(), GlobalErrorCode.BOARD_NOT_FOUND);
    }
  }
}
