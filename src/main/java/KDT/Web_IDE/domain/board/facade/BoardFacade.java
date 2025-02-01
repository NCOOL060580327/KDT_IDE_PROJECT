package KDT.Web_IDE.domain.board.facade;

import org.springframework.stereotype.Component;

import KDT.Web_IDE.domain.board.dto.request.CreateBoardRequestDto;
import KDT.Web_IDE.domain.board.dto.request.UpdateBoardRequestDto;
import KDT.Web_IDE.domain.board.dto.response.GetBoardResponseDto;
import KDT.Web_IDE.domain.board.entity.Board;
import KDT.Web_IDE.domain.board.entity.BoardUser;
import KDT.Web_IDE.domain.board.service.BoardCommandService;
import KDT.Web_IDE.domain.board.service.BoardQueryService;
import KDT.Web_IDE.domain.board.service.BoardUserCommandService;
import KDT.Web_IDE.domain.board.service.BoardUserQueryService;
import KDT.Web_IDE.domain.chat.entity.ChatRoom;
import KDT.Web_IDE.domain.chat.service.ChatRoomCommandService;
import KDT.Web_IDE.domain.chat.service.ChatRoomMemberCommandService;
import KDT.Web_IDE.domain.member.entity.Member;
import KDT.Web_IDE.domain.member.service.service.MemberQueryService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BoardFacade {

  private final BoardCommandService boardCommandService;
  private final BoardUserCommandService boardUserCommandService;
  private final BoardQueryService boardQueryService;
  private final BoardUserQueryService boardUserQueryService;

  private final ChatRoomCommandService chatRoomCommandService;
  private final ChatRoomMemberCommandService chatRoomMemberCommandService;

  private final MemberQueryService memberQueryService;

  public void createBoard(CreateBoardRequestDto requestDto, Long memberId) {

    Member member = memberQueryService.getMemberById(memberId);

    Board board = boardCommandService.createBoard(requestDto);

    ChatRoom chatRoom = chatRoomCommandService.creatChatRoom(board);

    BoardUser boardUser = boardUserCommandService.createBoardUser(board, member, true);

    chatRoomMemberCommandService.createChatRoomMember(boardUser, chatRoom);
  }

  public void updateBoard(UpdateBoardRequestDto requestDto, Long boardId, Long memberId) {
    Member member = memberQueryService.getMemberById(memberId);

    Board board = boardQueryService.getBoardByBoardId(boardId);

    boardUserQueryService.isLeader(member.getId(), boardId);

    boardCommandService.updateBoard(requestDto, board);
  }

  public void deleteBoard(Long boardId, Long memberId) {
    Member member = memberQueryService.getMemberById(memberId);

    Board board = boardQueryService.getBoardByBoardId(boardId);

    boardUserQueryService.isLeader(member.getId(), boardId);

    boardCommandService.deleteBoard(board);
  }

  public GetBoardResponseDto getBoard(Long boardId) {
    Board board = boardQueryService.getBoardByBoardId(boardId);

    return GetBoardResponseDto.fromEntity(board);
  }
}
