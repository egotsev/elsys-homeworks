#ifndef POINT_H
#define POINT_H

class Point {
	int x_, y_;
	
	public:
	
	Point(int x=0, int y=0) : x_(x), y_(y) {}
	
	int get_x() const {
		return x_;
	}	
	int get_y() const {
		return y_;
	}
};

#endif //POINT_H