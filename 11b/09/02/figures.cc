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

class Drawable {
public:
	virtual void draw() const = 0;
};

class Shape: public Styleable, public Drawable {
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

class Path : public Shape {
    list<string> options_;

    public:

    Path(list<string> options)
    : options_(options) {}

    Path() {}

    void add(string option) {
        options_.push_back(option);
    }

    void draw() const {
        cout << "<path d=\"";

        for(list<string>::const_iterator it = options_.begin(); it != options_.end(); it++){
            cout << *it << " ";
        }
        cout << "\" " << get_style();
        cout << "/>" << endl;
    }
};

class CompositeFigure : public Shape {
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

class Drawing : public Drawable {
	int width_, height_;
	CompositeFigure container_;
public:

    void add(Shape* shape) {
      container_.add(shape);
    }

    Drawing(int width, int height) : width_(width), height_(height) {}

	void draw() const {
	   container_.draw();
	}
};
