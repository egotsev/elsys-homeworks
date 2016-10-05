#include "maze.h"

int main()
{
    Board board = Board(20, 20);
    board.generate_maze(board.at(0, 0));
    board.draw(25);
	return 0;
}
