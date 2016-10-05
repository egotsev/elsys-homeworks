/*
    Homework:
    Implement the following figures:
    1. Ellipse +
    2. Rectangle + 
    3. Line +
    4. Polygon +
    5. Polyline
    6. Path +
    Deadline: 16.02.2016 17:00
    Place: https://github.com/egotsev/elsys-homeworks/11a/{number_in_class}/01
*/

#include <iostream>
#include <list>

using namespace std;

class Point 
{
    int x_, y_;
    
    public:
    
    Point(int x = 0, int y = 0) : x_(x), y_(y) {}
    
    int get_x() const 
    {
        return x_;
    }
    
    int get_y() const 
    {
        return y_;
    }
};

class Shape 
{
    public:
    
    virtual void draw() const = 0;
};

/*class Circle : public Shape {
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
};*/

class Ellipse : public Shape {
    Point center_;
    int radius_x_, radius_y_;
    const char *colour_;

public:

    Ellipse (Point center, int radius_x = 50, int radius_y = 80, const char* colour = "purple") : center_(center), radius_x_(radius_x), radius_y_(radius_y), colour_(colour) {}

    void draw() const
    {
     cout << "<ellipse cx =\"" << center_.get_x()
        << "\" cy =\"" << center_.get_y()
        << "\" rx = \"" << radius_x_
        << "\" ry = \"" << radius_y_
        << "\" style=\"fill:" << colour_
        << "\" />"
        << endl;   
    }

};

class Rectangle : public Shape 
{
    Point coord_;
    int width_, height_;
    const char *colour_;

public:
    Rectangle(Point coord, int width = 50, int height = 100, const char* colour = "pink") : coord_(coord), width_(width), height_(height), colour_(colour) {}

    void draw() const
    {
        cout << "<rect x =\"" << coord_.get_x()
        << "\" y =\"" << coord_.get_y()
        << "\" width = \"" << width_
        << "\" height = \"" << height_
        << "\" style=\"fill:" << colour_
        << "\" />"
        << endl;
    }

};

class Line : public Shape
{
    Point coord_;
    int x_, y_;
    const char* colour_;

public:

    Line(Point coord, int x = 50, int y = 50, const char* colour = "orange") : coord_(coord), x_(x + coord_.get_x()), y_(y + coord_.get_y()), colour_(colour) {}

    void draw() const
    {
        cout << "<line x1 =\"" << coord_.get_x()
        << "\" y1 =\"" << coord_.get_y()
        << "\" x2 = \"" << x_
        << "\" y2 = \"" << y_
        << "\" style=\"stroke:" << colour_
        << ";stroke-width:4\" />"
        << endl;

    }
};

class Polygon : public Shape
{
    Point point1_, point2_,point3_;
    const char* colour_;

    public:

        Polygon(Point point1, Point point2, Point point3, const char* colour = "Cyan") : point1_(point1), point2_(point2), point3_(point3), colour_(colour) {}

        void draw() const
        {
            cout << "<polygon points = \"" << point1_.get_x() << ", " << point1_.get_y()
            << " " << point2_.get_x() << ", " << point2_.get_y()
            << " " << point3_.get_x() << ", " << point3_.get_y()
            << "\" style=\"fill:" << colour_
            << "\" />"
            << endl;
        }

};

class Polyline : public Shape
{
    Point point1_, point2_, point3_, point4_, point5_;
    const char* colour_;
    
public:

    Polyline(Point point1, Point point2, Point point3, Point point4, Point point5, const char* colour = "thistle") : point1_(point1), point2_(point2), point3_(point3), point4_(point4), point5_(point5), colour_(colour) {}

    void draw() const
    {
        cout << "<polyline points=\"" << point1_.get_x() << "," << point1_.get_y()
        << " " << point2_.get_x() << ", " << point2_.get_y()
        << " " << point3_.get_x() << ", " << point3_.get_y()
        << " " << point4_.get_x() << ", " << point4_.get_y()
        << " " << point5_.get_x() << ", " << point5_.get_y()
        << "\" style=\"fill:none;stroke:" << colour_ << ";stroke-width:4\" />"
        << endl;
    }
};

class Path : public Shape
{
    string command_;
    const char* colour_;

public:

    Path(string command, const char* colour = "lightsalmon") : command_(command), colour_(colour) {}

    void draw() const
    {
        cout << "<path d=\"" << command_ 
        << "\" style=\"fill:none;stroke:" << colour_ << ";stroke-width:4\" />"
        << "\"/>" 
        << endl;
    }

};
//-----------------------------------------------------
class CompositeFigure : public Shape 
{
    list<Shape*> content_;
    
    public:
    
    ~CompositeFigure() 
    {
        for (list<Shape*>::iterator it = content_.begin(); it != content_.end(); it++) 
        {
            delete *it;
        }
    }

    void add(Shape* shape) 
    {
        content_.push_back(shape);
    }
    
    void draw() const 
    {
        for (list<Shape*>::const_iterator it = content_.begin(); it != content_.end(); it++) 
        {
            (*it)->draw();
        }
    }
};

class Canvas : public CompositeFigure 
{
    
    int width_, height_;
    
    public:
    
    Canvas(int width = 100, int height = 100) : width_(width), height_(height) {}
    
    void draw() const {
        cout << "<svg width=\"" << width_ << "\" height=\"" << height_ << "\" xmlns=\"http://www.w3.org/2000/svg\">" << endl;
        CompositeFigure::draw();
        cout << "</svg>" << endl;
    }
    
};


int main() 
{
    Canvas c(1000, 1000);
    c.add(new Ellipse(Point(100, 150)));
    c.add(new Rectangle(Point(325, 10), 50, 100));
    c.add(new Line(Point(400, 10)));
    c.add(new Polygon(Point(50,430),Point(150,550),Point(30,600)));
    c.add(new Polyline(Point(230,40),Point(200,70),Point(230,100),Point(200,130),Point(230,160)));
    c.add(new Path("M330 150 L415 200 L365 350 Z"));
    c.draw();
    return 0;
}