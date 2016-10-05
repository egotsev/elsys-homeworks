#ifndef MAZE_H
#define MAZE_H

#include <iostream>
#include <vector>
#include <cstdlib>
#include "figure.h"

using namespace std;

enum Direction {
    UP = 1,
    RIGHT = 1 << 1,
    DOWN = 1 << 2,
    LEFT = 1 << 3
};

class MazeError {};

class Cell {
    friend class Board;

    int row_, col_;
    bool visited_;
    int walls_;
    Cell* up_;
    Cell* right_;
    Cell* down_;
    Cell* left_;
    
    void set_up(Cell* c);
    
    void set_down(Cell* c);
    
    void set_right(Cell* c);
    
    void set_left(Cell* c);
    
    public:
    
    Cell(int row, int col) : visited_(false), row_(row), col_(col), 
    walls_(UP | RIGHT | DOWN | LEFT),
    up_(0), right_(0), down_(0), left_(0) {}
    
    void drill(Direction dir);
    
    void drill_to(Cell* next);
    
    bool has_wall(Direction dir) const;
    
    CompositeFigure* draw(int length) const;
    
    Cell* up();
    
    Cell* right();
    
    Cell* down();
    
    Cell* left();
    
    void visit();
    
    bool is_visited();
    
    bool has_unvisited_neighbours();
    
    vector<Cell*> get_unvisited_neighbours();
    
    Cell* get_random_unvisited_neighbour();
};

class Board {
    vector<Cell> cells_;
    int rows_, cols_;
    
    public:
    
    Board(int rows, int cols);
    Cell& at(int i, int j);
    
    void draw(int size = 10) const;
    void generate_maze(Cell& start);
};
#endif
