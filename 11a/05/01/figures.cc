#include <iostream>
#include <list>
#include <string>
/*
	Homework: 
	Implement the following figures;
	1. Eclipse
	2. Rectangle
	3. Line
	4. Polygon
	5. Polyline
	6. Path
	Deadline: 16.02.2016 17:00 
	Place: https://github.com/egotsev/elsys-homeworks/11a/{number_in_class}/01
*/


using namespace std;

class Point {
	int x_, y_;
	
	public:
	
	Point(int x = 0, int y = 0) : x_(x), y_(y) {}
	
	
	int get_x() const {
		return x_;
	}
	
	int get_y() const {
		return y_;
	}
};

class Shape {
	public:
	
	virtual void draw() const = 0;
	
};

class Circle : public Shape {
	int radius_;
	Point center_;
	
	public:
	
	Circle(Point center, int radius) : center_(center), radius_(radius) {}
	
	void draw() const {
		cout << "<circle cx=\"" << center_.get_x()
		 << "\" cy=\"" << center_.get_y()
		 << "\" r=\""<< radius_ << "\" />"
		 << endl;
	}
	
};


class Eclipse : public Shape {
	Point radius_;
	Point center_;
	
	public:
	
	Eclipse(Point center, Point radius) : center_(center), radius_(radius) {}
	
	void draw() const {
		cout << "<ellipse cx=\"" << center_.get_x()  
		 << "\" cy=\"" << center_.get_y()
		 << "\" rx=\"" << radius_.get_x()
		 << "\" ry=\"" << radius_.get_y()
		 << "\" />"
		 << endl;
	}
	
};


class Rectangle : public Shape {
	Point coord_;
	Point dimensions_;
	
	public:
	
	Rectangle(Point coord, Point dimensions) : dimensions_(dimensions) ,coord_(coord) {}
	
	void draw() const {
		cout << "<rect x=\"" << dimensions_.get_x()  
		 << "\" y=\"" << dimensions_.get_y()
		 << "\" width=\"" << coord_.get_x()
		 << "\" height=\"" << coord_.get_y()
		 << "\" />"
		 << endl;
	}
	
};


class Line : public Shape {
	Point first_coord_;
	Point second_coord_;
	
	public:
	
	Line(Point first_coord, Point second_coord) : first_coord_(first_coord), second_coord_(second_coord) {}
	
	void draw() const {
		cout << "<line x1=\"" << first_coord_.get_x()  
		 << "\" y1=\"" << first_coord_.get_y()
		 << "\" x2=\"" << second_coord_.get_x()
		 << "\" y2=\"" << second_coord_.get_y()
		 << "\" style=\"stroke:rgb(255,0,0);stroke-width:2\" />"
		 << endl;
	}
	
};


class Polygon : public Shape {
	list<Point> content_;
	
	public:
	
	Polygon(list<Point> content) : content_(content) {}
	
	void draw() const {
		cout << "<polygon points=\"";
		for(list<Point>::const_iterator it = content_.begin(); it != content_.end(); it++) {
			cout << (*it).get_x() << "," << (*it).get_y() << " ";
		}
		cout <<"\" />"
		<< endl;
	}
	
};


class Polyline : public Shape {	
	list<Point> content_;
	
	public:
	
	Polyline(list<Point> content) : content_(content) {}
	
	void draw() const {
		cout << "<polyline points=\"";
		for(list<Point>::const_iterator it = content_.begin(); it != content_.end(); it++) {
			cout << (*it).get_x() << "," << (*it).get_y() << " ";
		}
		cout <<"\" style=\"fill:none;stroke:black;stroke-width:3\" />"
		<< endl;
	}
	
};

class Path : public Shape {
	string str_;
	
	public:

	Path(string str) : str_(str) {}
	
	void draw() const {
		cout << "<path d=\"" << str_
		<< "\" />" 
		<< endl;
	}

};


class CompositeFigure : public Shape {
	list<Shape*> content_;
	
	public:
	
	~CompositeFigure() {
		for(list<Shape*>::iterator it = content_.begin(); it != content_.end(); it++) {
			delete *it;
		}
	}
	
	void add(Shape* shape) {
		content_.push_back(shape);
	}
	
	void draw() const {
		for(list<Shape*>::const_iterator it = content_.begin(); it != content_.end(); it++) {
			(*it)->draw();
		}
	}

};

class Canvas : public CompositeFigure {
	int width_, height_;
	public:
	
	Canvas (int width = 100, int height = 100) : width_(width), height_(height) {}
	
	void draw() const {
		cout << "<svg width=\"" << width_ 
		<< "\" height=\"" << height_ 
		<< "\">" << endl;
		CompositeFigure::draw();
		cout << "</svg>" << endl;
	}
	
};


int main() {

	Canvas c(1000, 1000);
	c.add(new Circle(Point(800, 100), 40));	
	c.add(new Eclipse(Point(300, 100), Point(80, 40)));
	c.add(new Rectangle(Point(100, 200),Point(100, 300)));	
	c.add(new Line(Point(400,50), Point(600,100)));
	
	list<Point> polygon;
	polygon.push_back(Point(300,300));
	polygon.push_back(Point(400,400));
	polygon.push_back(Point(500,400));
	polygon.push_back(Point(600,300));
	
	list<Point> polyline;
	polyline.push_back(Point(100,600));
	polyline.push_back(Point(150,650));
	polyline.push_back(Point(300,650));
	polyline.push_back(Point(350,670));
	polyline.push_back(Point(500,600));
	
	c.add(new Polygon(polygon));
	c.add(new Polyline(polyline));
	
	c.add(new Path("M150 0 L75 200 L225 200 Z"));
	
	c.draw();
	return 0;
}









