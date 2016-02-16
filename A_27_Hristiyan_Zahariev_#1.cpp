#include<iostream>
#include<list>
using namespace std;

class Point {
	int x_,y_;
	
public:

	Point(int x=0, int y=0) : x_(x), y_(y) {}
	
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

	Circle(int radius, Point center) {
		radius_ = radius;
		center_ = center;
	}

	void draw() const {
		cout << "<circle cx=\"" << center_.get_x()
		     << "\" cy=\"" << center_.get_y()
		     << "\" r=\"" << radius_ 
		     << "\" />" << endl;
	}
};

class Rectangle : public Shape {
	int width_, height_;
	Point points_;

public:
	
	Rectangle(int width, int height, Point points) {
		width_ = width;
		height_ = height;
		points_ = points;
	}

	void draw() const {
		cout << "<rect x=\"" << points_.get_x()
		<< "\" y=\"" << points_.get_y()
		<< "\" width=\"" << width_
		<< "\" height=\"" << height_ 
		<< "\"/>" << endl;
	}

};

class Ellipse : public Shape {
	Point points_;
	int radius_x_, radius_y_;

	public:

	Ellipse(int radius_x, int radius_y, Point points) {
		radius_x_ = radius_x;
		radius_y_ = radius_y;
		points_ = points;

	}
	void draw() const {
		cout << "<ellipse cx=\"" << points_.get_x()
		<< "\" cy=\"" << points_.get_y()
		<< "\" rx=\"" << radius_x_
		<< "\" ry=\"" << radius_y_ 
		<< "\"/>" << endl;
	}
};

class Line : public Shape {
	Point points_x_;
	Point points_y_;

public:

	Line(Point points_x, Point points_y) {
		points_x_ = points_x;
		points_y_ = points_y;
	}

	void draw() const {
		cout << "<line x1=\"" << points_x_.get_x()
		<< "\" y1=\"" << points_x_.get_y()
		<< "\" x2=\"" << points_y_.get_x()
		<< "\" y2=\"" << points_y_.get_y()
		<< "\" style=\"stroke:black;stroke-width:2" //без style не ми го чертае
		<< "\"/>" << endl;
	}
};

class Polyghon : public Shape {
	Point points_x_;
	Point points_y_;
	Point points_z_;

public:

	Polyghon(Point points_x, Point points_y, Point points_z) {
		points_x_ = points_x;
		points_y_ = points_y;
		points_z_ = points_z;
	}

	void draw() const {
		cout << "<polygon points=\"" << points_x_.get_x()
		<< "," << points_x_.get_y()
		<< " " << points_y_.get_x()
		<< "," << points_y_.get_y()
		<< " " << points_z_.get_x()
		<< "," << points_z_.get_y()
		<< "\"/>" << endl;
	}

};

class Polyline : public Shape {
	Point points_1_;
	Point points_2_;
	Point points_3_;
	Point points_4_;
	Point points_5_;
	Point points_6_;

public:

	Polyline(Point points_1,Point points_2,Point points_3,Point points_4,Point points_5,Point points_6) {
		points_1_ = points_1;
		points_2_ = points_2;
		points_3_ = points_3;
		points_4_ = points_4;
		points_5_ = points_5;
		points_6_ = points_6; 
	}

	void draw() const {
		cout << "<polyline points=\"" << points_1_.get_x()
		<< "," << points_1_.get_y()
		<< " " << points_2_.get_x()
		<< "," << points_2_.get_y()
		<< " " << points_3_.get_x()
		<< "," << points_3_.get_y()
		<< " " << points_4_.get_x()
		<< "," << points_4_.get_y()
		<< " " << points_5_.get_x()
		<< "," << points_5_.get_y()
		<< " " << points_6_.get_x()
		<< "," << points_6_.get_y()
		<< "\" style=\"fill:none;stroke:black;stroke-width:2" //слагам style, защото иначе не прилича на polyline
		<< "\"/>" << endl;

	}

};

class Path : public Shape {
	Point points_x_;
	Point points_y_;
	Point points_z_;

public:

	Path(Point points_x, Point points_y, Point points_z) {
		points_x_ = points_x;
		points_y_ = points_y;
		points_z_ = points_z;
	}

	void draw() const {
		cout << "<path d=\"M" << points_x_.get_x()
		<< " " << points_x_.get_y()
		<< " L" << points_y_.get_x()
		<< " " << points_y_.get_y()
		<< " L" << points_z_.get_x()
		<< " " << points_z_.get_y()
		<< " Z"
		<< "\"/>" << endl;
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

class Canvas : public CompositeFigure {
    
    int width_, height_;
    
    public:
    
    Canvas(int width, int height) {
    	width_ = width;
    	height_ = height;
    }
    
    void draw() const {
        cout << "<svg width=\"" << width_ 
            << "\" height=\"" << height_
            << "\">" << endl;
        CompositeFigure::draw();
        cout << "</svg>" << endl;
    }
    
};

int main() {
	Canvas canvas(850,900);
	canvas.add(new Circle(50,Point(200,400)));
	canvas.add(new Rectangle(250,100,Point(50,70)));
	canvas.add(new Ellipse(100, 50,Point(300, 300)));
	canvas.add(new Line(Point(600,400), Point(800,700)));
	canvas.add(new Polyghon(Point(500,300), Point(550, 490), Point(460,510)));
	canvas.add(new Polyline(Point(450,650), Point(450,500), Point(300,350),Point(450,730),Point(620,640), Point(700,680)));
	canvas.add(new Path(Point(700,100), Point(600,300), Point(800,200)));
	canvas.draw();
	
	return 0;
}