#include "maze.h"
#include "figure.h"


void Cell::set_up(Cell* c) {
    up_ = c;
}

void Cell::set_down(Cell* c) {
    down_ = c;
}

void Cell::set_right(Cell* c) {
    right_ = c;
}

void Cell::set_left(Cell* c) {
    left_ = c;
}
    
void Cell::drill(Direction dir) {
    //dir = 1000
    //~dir = 0111
    //walls_ = 1111
    //walls_ & ~dir = 0111
    walls_ &= ~dir;
}
    
void Cell::drill_to(Cell* next) {
    if (next == up()) {
        drill(UP);
        next->drill(DOWN);
    } else if (next == right()) {
        drill(RIGHT);
        next->drill(LEFT);
    } else if (next == down()) {
        drill(DOWN);
        next->drill(UP);
    } else if (next = left()) {
        drill(LEFT);
        next->drill(RIGHT);
    } else {
        throw MazeError();
    }
}

bool Cell::has_wall(Direction dir) const {
    // walls_ = 1011
    // dir =    1000 => 1000
    // dir =    0010 => 0010
    // dir =    0100 => 0000
    return walls_ & dir;
}

CompositeFigure* Cell::draw(int length) const {
//    cout << col_*length << " " << row_*length << " moveto" << endl;
//    cout << length << " " << 0 << (has_wall(DOWN) ? " rlineto" : " rmoveto") << endl;
//    cout << 0 << " " << length << (has_wall(RIGHT) ? " rlineto" : " rmoveto")  << endl;
//    cout << -length << " " << 0 << (has_wall(UP) ? " rlineto" : " rmoveto") << endl;
//    cout << 0 << " " << -length << (has_wall(LEFT) ? " rlineto" : " rmoveto") << endl;
    CompositeFigure* figure = new CompositeFigure();
    if(has_wall(DOWN))
    {
        //Point from = Point();
        Line * line = new Line(Point(col_*length, row_*length), Point((col_+1)*length, row_*length));
        line->set_property("stroke-width", "3");
        line->set_property("stroke", "blue");
        figure->add(line);
    }
    if(has_wall(RIGHT))
    {
        //Point from = Point();
        Line * line = new Line(Point((col_+1)*length, row_*length), Point((col_+1)*length, (row_+1)*length));
        line->set_property("stroke-width", "3");
        line->set_property("stroke", "blue");
        figure->add(line);
    }
    if(has_wall(UP))
    {
        //Point from = Point();
        Line * line = new Line(Point((col_+1)*length, (row_+1)*length), Point(col_*length, (row_+1)*length));
        line->set_property("stroke-width", "3");
        line->set_property("stroke", "blue");
        figure->add(line);
    }
    if(has_wall(LEFT))
    {
        //Point from = Point();
        Line * line = new Line(Point(col_*length, (row_+1)*length), Point(col_*length, row_*length));
        line->set_property("stroke-width", "3");
        line->set_property("stroke", "blue");
        figure->add(line);
    }
    return figure;
}

Cell* Cell::up() {
    return up_;
}

Cell* Cell::right() {
    return right_;
}

Cell* Cell::down() {
    return down_;
}

Cell* Cell::left() {
    return left_;
}

void Cell::visit() {
    visited_ = true;
}

bool Cell::is_visited() {
    return visited_; 
}

bool Cell::has_unvisited_neighbours() {
    return !get_unvisited_neighbours().empty();
}

vector<Cell*> Cell::get_unvisited_neighbours() {
    vector<Cell*> neighbours;
    if (up() != 0 && !up()->is_visited()) {
        neighbours.push_back(up());
    }
    if (right() != 0 && !right()->is_visited()) {
        neighbours.push_back(right());
    }
    if (down() != 0 && !down()->is_visited()) {
        neighbours.push_back(down());
    }
    if (left() != 0 && !left()->is_visited()) {
        neighbours.push_back(left());
    }
    return neighbours;
}

Cell* Cell::get_random_unvisited_neighbour() {
    if (!has_unvisited_neighbours()) {
        throw MazeError();
    }
    vector<Cell*> neighbours = get_unvisited_neighbours();
    int index = rand() % neighbours.size();
    return neighbours[index];
}

Board::Board(int rows, int cols) : rows_(rows), cols_(cols) {
    for (int i = 0; i < rows_; i++) {
        for(int j = 0; j < cols_; j++) {
            cells_.push_back(Cell(i, j));
        }
    }
    
    for (int i = 0; i < rows_; i++) {
        for(int j = 0; j < cols_; j++) {
            Cell& c = at(i, j);
            if (i < rows_ - 1)
                c.set_up(&at(i + 1,j));
            if (i > 0)
                c.set_down(&at(i - 1, j));
            if (j < cols_ - 1)
                c.set_right(&at(i, j + 1));
            if (j > 0)
                c.set_left(&at(i, j - 1));
        }
    }
}

Cell& Board::at(int i, int j) {
    return cells_[i * cols_ + j];
}

void Board::draw(int size) const {
    Canvas canvas = Canvas((cols_+1)*size, (rows_+1)*size);
    
        
    for(vector<Cell>::const_iterator it = cells_.begin();
        it!=cells_.end(); ++it) {
        canvas.add((*it).draw(size));
    }
    canvas.draw();
}

void Board::generate_maze(Cell& start) {
    start.visit();
    while (start.has_unvisited_neighbours()) {
        Cell* next = start.get_random_unvisited_neighbour();
        start.drill_to(next);
        generate_maze(*next);
    }
}