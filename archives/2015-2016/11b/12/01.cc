/*
	4. Add Polyline
	5. Add Polygone
	6. Add path
*/

#include <iostream>
#include <list>
#include <string>
#include <sstream>
using namespace std;

class Point {
	int x_;
	int y_;
public:
	Point(int x=0, int y=0)
	: x_(x), y_(y)
	{}
	
	int get_x() const {
		return x_;
	}
	
	int get_y() const {
		return y_;
	}
};

class Styleable {
	string stroke_;
	string fill_;
	string style_;
public:
	Styleable()
	{}
	
	Styleable& set_stroke(string color) {
		stroke_=color;
		return *this;
	}
	
	Styleable& set_style(string style) {
		style_ = style;
		return *this;
	}
	
	string get_style() const {
		string result;
		if(!style_.empty()) {
			result = " style=\"" + style_ + "\" ";
			return result;
		}
		
		if(!stroke_.empty()) {
			result += " stroke=\"" + stroke_ + "\" ";
		}
		return result;
	}
	
};


class Shape: public Styleable {
public:
	virtual void draw() const = 0;
};


class Circle: public Shape {
	Point center_;
	int r_;
public:
	Circle(Point center, int r)
	: center_(center),
	  r_(r)
	{}
	
	void draw() const {
		cout << "<circle cx=\"" << center_.get_x() 
			 << "\" cy=\"" << center_.get_y()
			 << "\" r=\"" << r_
			 << "\" " << get_style()
			 << "/>" << endl;
	}
};

class Rectangle: public Shape {
	Point size_;
public:
	Rectangle(Point size)
	: size_(size)
	{}
	
	void draw() const {
		cout << "<rect width=\"" << size_.get_x() 
			 << "\" height=\"" << size_.get_y()
			 << "\" " << get_style()
			 << "/>" << endl;
	}
};

class Elipse: public Shape {
	Point center_;
	int rx_,ry_;
public:
	Elipse(Point center, int rx, int ry)
	: center_(center),rx_(rx),ry_(ry)
	{}
	
	void draw() const {
		cout << "<ellipse cx=\"" << center_.get_x() 
			 << "\" cy=\"" << center_.get_y()
			 << "\" rx=\"" << rx_
			 << "\" ry=\"" << ry_
			 << "\" " << get_style()
			 << "/>" << endl;
	}
};

class Line: public Shape {
	Point point1_;
	Point point2_;
public:
	Line(Point point1,Point point2)
	: point1_(point1),point2_(point2)
	{}
	
	void draw() const {
		cout << "<line x1=\"" << point1_.get_x() 
			 << "\" y1=\"" << point1_.get_y()
			 << "\" x2=\"" << point2_.get_x()
			 << "\" y2=\"" << point2_.get_y()
			 << "\" " << get_style()
			 << "/>" << endl;
	}
};

class Drawing {
	list<Shape*> shapes_;
public:
	void add(Shape* shape) {
		shapes_.push_back(shape);
	}

	void draw() const {
		cout << "<svg>" << endl;
		for(list<Shape*>::const_iterator it = shapes_.begin();
			it!=shapes_.end(); ++it) {
		
			(*it)->draw();	
		}
		cout << "</svg>" << endl;
	}
};

int main() {
	
	Drawing d;
	
	for(int i=0; i<5; ++i) {
		Circle* pc = new Circle(Point(i*10, i*10), i*5);
		ostringstream style;
		style << "stroke: red;"
			  << "stroke-width: " << 0.1*i << "; "
			  << "fill: rgb(" << i << "," << i << ", " << 3*i << ");"
			  << "fill-opacity: " << 0.1 + i*.005 << ";";
			  
		pc->set_style(style.str());
		d.add(pc);
	}
	
	
	d.draw();
	
	return 0;
}
