package KDT.Web_IDE.domain.board.entity.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import KDT.Web_IDE.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {}
