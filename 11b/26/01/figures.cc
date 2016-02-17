/*
    Homework:
    Implement the following figures:
    1. Ellipse #DONE
    2. Rectangle #DONE
    3. Line #DONE
    4. Polygon #DONE
    5. Polyline #DONE
    6. Path #DONE
    Deadline: 16.02.2016 17:00
    Place: https://github.com/egotsev/elsys-homeworks/11a/{number_in_class}/01
*/

#include <iostream>
#include <list>

using namespace std;

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

    static void draw(Point point) {
        cout << point.get_x() << "," << point.get_y() << " ";
    }

    static void draw(list<Point> points_) {
        for (list<Point>::const_iterator it = points_.begin();
             it != points_.end();
             it++) {
            draw(*it);
        }
    }
};

class Shape {
public:
    virtual void draw() const=0;
};

class Circle : public Shape {
    int radius_;
    Point center_;

public:
    Circle(Point center, int radius): center_(center), radius_(radius) {}

    void draw() const {
        cout << "<circle cx=\"" << center_.get_x()
            << "\" cy=\"" << center_.get_y()
            << "\" r=\"" << radius_ << "\" />"
            << endl;
    }
};

class Ellipse : public Shape {
    Point center_;
    int rx_;
    int ry_;

public:
    Ellipse(Point center, int rx, int ry)
    : center_(center), rx_(rx), ry_(ry) {}

    void draw() const {
        cout << "<ellipse cx=\"" << center_.get_x() << "\""
            << " cy=\"" << center_.get_y() << "\""
            << " rx=\"" << rx_ << "\""
            << " ry=\"" << ry_ << "\" />"
            << endl;
    }
};

class Rectangle : public Shape {
    int x_;
    int y_;
    int width_;
    int height_;

public:
    Rectangle(int x, int y, int width, int height)
    : x_(x), y_(y), width_(width), height_(height) {}

    void draw() const {
        cout << "<rectangle x=\"" << x_ << "\""
            << " y=\"" << y_ << "\""
            << " width=\"" << width_ << "\""
            << " height=\"" << height_ << "\" />"
            << endl;
    }
};

class Line : public Shape {
    int x1_;
    int y1_;
    int x2_;
    int y2_;

public:
    Line(int x1, int y1, int x2, int y2)
    : x1_(x1), y1_(y1), x2_(x2), y2_(y2) {}

    void draw() const {
        cout << "<line x1=\"" << x1_ << "\""
            << " y1=\"" << y1_ << "\""
            << " x2=\"" << x2_ << "\""
            << " y2=\"" << y2_ << "\" />"
            << endl;
    }
};

class Polygon : public Shape {
protected:
    list<Point> points_;
public:
    Polygon(list<Point> points): points_(points) {}

    void draw() const {
        cout << "<polygon points=\"";
        Point::draw(points_);
        cout << "\" />" << endl;
    }
};

class Polyline : public Polygon {
public:
    Polyline(list<Point> points_): Polygon(points_) {}
    void draw() const {
        cout << "<polyline points=\"";
        Point::draw(points_);
        cout << "\" />" << endl;
    }
};

class Path : public Shape {
    string definition_;

public:
    Path(string definition): definition_(definition) {}

    void draw() const {
        cout << "<path d=\"" << definition_ << "\" />" << endl;
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
    Canvas(int width=100, int height=100) : width_(width), height_(height) {}

    void draw() const {
        cout << "<svg width=\"" << width_
            << "\" height=\"" << height_
            << "\">" << endl;
        CompositeFigure::draw();
        cout << "</svg>" << endl;
    }

};


int main() {
    Canvas c(400, 600);

    c.add(new Circle(Point(20, 20), 15));
    c.add(new Ellipse(Point(50, 100), 10, 10));
    c.add(new Rectangle(50, 20, 150, 150));
    c.add(new Line(0, 0, 200, 200));

    list<Point> polygon_points;
    polygon_points.push_back(Point(220, 10));
    polygon_points.push_back(Point(300, 210));
    polygon_points.push_back(Point(170, 250));
    c.add(new Polygon(polygon_points));

    list<Point> polyline_points;
    polyline_points.push_back(Point(20, 20));
    polyline_points.push_back(Point(40, 25));
    polyline_points.push_back(Point(60, 40));
    c.add(new Polyline(polyline_points));

    c.add(new Path("M150 0 L75 200 L225 200 Z"));

    c.draw();
    return 0;
}
