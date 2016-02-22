

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
	void set_x(int val){
	x_ = val;
	}
	void set_y(int val){
		y_ = val;

	}
	 void draw() {
		std::cout << x_<< ","<< y_ << " ";
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

	Styleable& set_style(string style = "stroke:rgb(255,0,0);stroke-width:2") {
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
class Line: public Shape{
	Point start_;
	Point end_;
public:
	Line(Point start , Point end)
	: start_(start),
	end_(end)
	{set_style("stroke: black; stroke-width: 2");}

		void draw() const{

  	cout << "<line x1=\""<<start_.get_x()
		<<"\" y1=\""<<start_.get_y()
		<< "\" x2=\""<< end_.get_x()
		<<"\" y2=\"" << end_.get_y()<<
		 "\""<<get_style() << " />" << endl;
		}

};


class Path : public Shape {
    list<pair<char, Point> > path_;
public:
    Path(list<pair<char, Point> > path) : path_(path) {}

    void draw() const {
        cout << "<path d=\"";
        for(list<pair<char, Point> >::const_iterator it = path_.begin(); it != path_.end(); it++) {
            cout << it -> first << " ";
            cout <<  it -> second.get_x() << "," << it -> second.get_y();
        }

        cout << "\" style=\"fill:none\" stroke=\"black\" />" << endl;
    }
};


class Drawing {
	list<Shape*> shapes_;
public:
	void add(Shape* shape) {
		shapes_.push_back(shape);
	}

	void draw() const {
		for(list<Shape*>::const_iterator it = shapes_.begin();
			it!=shapes_.end(); ++it) {

			(*it)->draw();
		}
	}
};
