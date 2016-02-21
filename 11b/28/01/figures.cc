/*
	HW:
	1. elipse - DONE
	2. line - DONE
	3. polygon - DONE
	4. polyline - DONE
	5. rectangle - DONE
*/

#include <iostream>
#include <string>
#include <list>
#include <sstream>

using namespace std;

class Point {
	int x_;
	int y_;
public:
	Point(int x = 0, int y = 0) : x_(x), y_(y) {}

	int get_x() const {
		return x_;
	}
	int get_y() const {
		return y_;
	}

	static void draw(list<Point> points) {
		for(list<Point>::const_iterator it = points.begin(); it != points.end(); it++) {
			cout << (*it).get_x() << "," << (*it).get_y() << " ";
		}
	}

	void draw() const {
		cout << x_ << "," << y_ << " ";
	}
};

class Styleable {
	string stroke_;
	string fill_;
	string style_;
public:
	Styleable() {}

	Styleable& set_stroke(string color) {
		stroke_ = color;

		return *this;
	}

	Styleable& set_style(string style) {
		style_ = style;

		return *this;
	}

	string get_style() const {
		string result = "";

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

class Shape : public Styleable, public Drawable {
public:
	virtual void draw() const = 0;
};

class Circle : public Shape {
	Point center_;
	int radius_;
public:
	Circle(Point center, int radius) : center_(center), radius_(radius) {}

	void draw() const {
		cout << "<circle cx=\"" << center_.get_x()
			<< "\" cy=\"" << center_.get_y()
			<< "\" r=\"" << radius_
			<< "\" " << get_style()
			<< "/>" << endl;
	}
};

class Rectangle : public Shape {
	int width_;
	int height_;
public:
	Rectangle(int width, int height) : width_(width), height_(height) {}

	void draw() const {
		cout << "<rect width=\"" << width_
			<< "\" height=\"" << height_
			<< "\" " << get_style()
			<< "/>" << endl;
	}
};

class Ellipse : public Shape {
	Point center_;
	Point radcenter_;
public:
	Ellipse(Point center, Point radcenter) : center_(center), radcenter_(radcenter) {}

	void draw() const {
		cout << "<ellipse cx=\"" << center_.get_x()
			<< "\" cy=\"" << center_.get_y()
			<< "\" rx=\"" << radcenter_.get_x()
			<< "\" ry=\"" << radcenter_.get_y()
			<< "\" " << get_style()
			<< "/>" << endl;
	}
};

class Line : public Shape {
	Point one_;
	Point two_;
public:
	Line(Point one, Point two) : one_(one), two_(two) {}

	void draw() const {
		cout << "<line x1=\"" << one_.get_x()
			<< "\" y1=\"" << one_.get_y()
			<< "\" x2=\"" << two_.get_x()
			<< "\" y2=\"" << two_.get_y()
			<< "\" " << get_style()
			<< "/>" << endl;
	}
};

class Polygon : public Shape {
	list<Point> points_;
public:
	Polygon(list<Point> points) : points_(points) {}

	void draw() const {
		cout << "<polygon points=\"";
		Point::draw(points_);
		cout << "\" />" << endl;
	}
};

class Polyline : public Shape {
	list<Point> points_;
public:
	Polyline(list<Point> points) : points_(points) {}

	void draw() const {
		cout << "<polyline points=\"";
		Point::draw(points_);
		cout << "\" style=\"fill:none;stroke:black;stroke-width:3\""
		<< " />" << endl;
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
			it -> second.draw();
		}

		cout << "\" />" << endl;
	}
};

class CompositeFigure : public Shape {
    list<Shape*> content_;
    
    public:
    
    ~CompositeFigure() {
        for (list<Shape*>::iterator it = content_.begin(); it != content_.end(); it++) {
            delete *it;
        }
    }

    void add(Shape* shape) {
        content_.push_back(shape);
    }
    
    void draw() const {
        for (list<Shape*>::const_iterator it = content_.begin(); it != content_.end(); it++) {
            (*it)->draw();
        }
    }
};

class Canvas : public Drawable {
    
    CompositeFigure content_;
    int width_, height_;
    
    public:
    
    Canvas(int width=100, int height=100) : width_(width), height_(height) {}
    
    void add(Shape* shape) {
        content_.add(shape);
    }
    
    void draw() const {
        cout << "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" << width_ 
            << "\" height=\"" << height_
            << "\">" << endl;
        content_.draw();
        cout << "</svg>" << endl;
    }
    
};

class Drawing : public Drawable {
	int width_;
	int height_;

	CompositeFigure container_;
public:
	void add(Shape* shape) {
		container_.add(shape);
	}

	Drawing(int width, int height) : width_(width), height_(height) {}

	void draw() const {
		cout //<< "<svg width=\"" << width_
			//<< "\" height=\"" << height_
			<< "<svg xmlns=\"http://www.w3.org/2000/svg\">" << endl;
		container_.draw();
		cout << "</svg>" << endl;
	}
};

int main() {
	//Circle c1(Point(10,10), 100);
	//c1.set_stroke("green");
	//Rectangle r1(100, 300);
	//r1.set_stroke("green");
	//Line l1(Point(0,0), Point(200, 200));
	//l1.set_stroke("red");

	//Canvas c;
	Drawing d(400, 600);
	//d.add(&c1);
	//d.add(&r1);
	//d.add(&l1);

	//list<Point> p1;
	//p1.push_back(Point(20, 20));
	//p1.push_back(Point(40, 25));
	//p1.push_back(Point(60, 40));
	
	//d.add(new Polyline(p1));
	//d.draw();

	/*
	list<pair<char, Point> > p;
	p.push_back(make_pair('M', Point(150, 0)));
	p.push_back(make_pair('L', Point(75, 200)));
	p.push_back(make_pair('L', Point(225, 200)));
	p.push_back(make_pair('Z', Point(150, 0)));
	Path* paath = new Path(p);
	d.add(paath);
	*/

	
	for (int i = 0; i < 500; ++i) {
		/*
		list<Point> p1;
		p1.push_back(Point(20*i, 1*i));
		p1.push_back(Point(25*i, 19*i));
		p1.push_back(Point(16*i, 21*i));
		p1.push_back(Point(12*i, 15*i));
		*/
		list<pair<char, Point> > p;
		p.push_back(make_pair('M', Point(15*i, 0*i)));
		p.push_back(make_pair('L', Point(7*i, 20*i)));
		p.push_back(make_pair('L', Point(22*i, 20*i)));
		p.push_back(make_pair('Z', Point(15*i, 0*i)));

		//Circle* pc = new Circle(Point(i*10, i*10), i*5);
		//Rectangle* pr = new Rectangle(i*10, i*2);
		//Ellipse* pe = new Ellipse(Point(200, 80), Point(100, 50));
		//Line* pl = new Line(Point(i, i), Point(2*i, 2*i));
		//Polygon* p_polygon = new Polygon(p1);
		//Polyline* p_polyline = new Polyline(p1);
		Path* paath = new Path(p);

		ostringstream style;
		style << "stroke: green;"
			<< "stroke-width: " << 0.1*i << "; "
			<< "fill: rgb(" << i*2 << "," << i << ", " << i << ");";

		//pc->set_style(style.str());
		//d.add(pc);
		//pr -> set_style(style.str());
		//d.add(pr);
		//pe -> set_style(style.str());
		//d.add(pe);
		//pl -> set_style(style.str());
		//d.add(pl);
		//p_polygon -> set_style(style.str());
		//d.add(p_polygon);
		//p_polyline -> set_style(style.str());
		//d.add(p_polyline);
		paath -> set_style(style.str());
		d.add(paath);
	}
	d.draw();

	return 0;
}