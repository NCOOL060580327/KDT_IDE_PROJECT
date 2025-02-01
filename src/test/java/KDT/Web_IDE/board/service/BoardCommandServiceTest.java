package KDT.Web_IDE.board.service;

import static KDT.Web_IDE.board.constant.BoardTestConstant.TITLE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import KDT.Web_IDE.domain.board.dto.request.CreateBoardRequestDto;
import KDT.Web_IDE.domain.board.dto.request.UpdateBoardRequestDto;
import KDT.Web_IDE.domain.board.entity.Board;
import KDT.Web_IDE.domain.board.entity.repositroy.BoardRepository;
import KDT.Web_IDE.domain.board.service.BoardCommandService;

@ActiveProfiles("dev")
public class BoardCommandServiceTest {

  @Mock private BoardRepository boardRepository;

  @InjectMocks private BoardCommandService boardCommandService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Nested
  @DisplayName("게시판을")
  class board {

    Board mockBoard = mock(Board.class);

    @Test
    @DisplayName("생성한다.")
    void createBoardSuccess() {
      // give
      CreateBoardRequestDto requestDto = new CreateBoardRequestDto(TITLE.getValue());
      when(boardRepository.save(any(Board.class))).thenReturn(mockBoard);

      // when
      Board board = boardCommandService.createBoard(requestDto);

      // then
      verify(boardRepository, times(1)).save(any(Board.class));
    }

    @Test
    @DisplayName("수정한다.")
    void updateBoardSuccess() {
      // give
      UpdateBoardRequestDto requestDto = new UpdateBoardRequestDto(TITLE.getValue());

      // when
      boardCommandService.updateBoard(requestDto, mockBoard);

      // then
      verify(mockBoard, times(1)).setTitle(TITLE.getValue());
    }

    @Test
    @DisplayName("삭제한다.")
    void deleteBoardSuccess() {
      // when
      boardCommandService.deleteBoard(mockBoard);

      // then
      verify(boardRepository, times(1)).delete(mockBoard);
    }
  }
}
