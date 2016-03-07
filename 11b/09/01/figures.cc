#include <iostream>
#include <list>
#include <string>
#include <sstream>
using namespace std;

class Styleable {
  string stroke_;
  string fill_;
  string style_;
public:
  Styleable(){}

  Styleable& set_stroke(string color){
    stroke_=color;
    return *this;
  }

  Styleable& set_style(string style){
    style_ = style;
    return *this;
  }
  string get_style() const {
    string result;
    if(!style_.empty()){
      result = " style=\""+style_+"\"";
      return result;
    }
    if(!stroke_.empty()){
      result += " stroke=\""+stroke_+"\" ";
    }
    return result;
  }
};

class Point{
  int x_;
  int y_;
public:
  Point(int x=0, int y=0)
  :x_(x), y_(y)
  {}

  int get_x() const {
    return x_;
  }

  int get_y() const {
    return y_;
  }
};

class Shape: public Styleable  {

public:
  virtual void draw() const = 0;
};

class Circle: public Shape{
  Point center_;
  int r_;
public:
  Circle(Point center, int r)
  : center_(center),
    r_(r)
  {}

  void draw() const {
    cout << "<circle cx=\"" << center_.get_x() << "\" cy=\"" << center_.get_y() << "\" r=\"" << r_ <<"\""<< get_style() << "/>" << endl;
  }
};

class Rectangle: public Shape{
  Point left_;
  int width_;
  int height_;
public:
  Rectangle(Point left, int w, int h)
  : left_(left),
    width_(w),
    height_(h)
    {}

  void draw() const{
    cout << "<rect x=\""<< left_.get_x() << "\" y=\"" << left_.get_y() << "\" width=\"" << width_ << "\" height=\"" << height_ <<"\"" << get_style() << "/>" << endl;
  }
};

class Ellipse: public Shape{
  Point center_;
  int rx_;
  int ry_;
public:
  Ellipse(Point center, int rx, int ry)
  : center_(center),
    rx_(rx),
    ry_(ry)
    {}

  void draw() const {
    cout << "<ellipse cx=\"" << center_.get_x() << "\" cy=\"" << center_.get_y() << "\" rx=\"" << rx_ <<"\" ry=\""<< ry_ << "\"" << get_style() << "/>" << endl;
  }
};

class Line: public Shape{
  Point p1_;
  Point p2_;
public:
  Line(Point p1, Point p2)
  :p1_(p1),
   p2_(p2)
   {}

  void draw() const {
    cout << "<line x1=\""<< p1_.get_x() <<"\" y1=\"" << p1_.get_y() << "\" x2=\"" << p2_.get_x() << "\" y2=\"" << p2_.get_y() << "\"" << get_style() << "/>" << endl;
  }
};

class Polyline: public Shape{
  Point p1_;
  Point p2_;
  Point p3_;
  Point p4_;
  Point p5_;
  Point p6_;
public:
  Polyline(Point p1, Point p2, Point p3, Point p4, Point p5, Point p6)
  :p1_(p1),
   p2_(p2),
   p3_(p3),
   p4_(p4),
   p5_(p5),
   p6_(p6)
  {}

  void draw() const{
    cout << "<polyline points=\""<< p1_.get_x()<<","<<p1_.get_y()<<" "<<p2_.get_x()<<","<<p2_.get_y()<<" "<<p3_.get_x()<<","<<p3_.get_y()<< " " << p4_.get_x()<<","<<p4_.get_y()<<" "<<p5_.get_x()<<","<<p5_.get_y()<<" "<<p6_.get_x()<<","<<p6_.get_y()<<"\""<< get_style() << "/>" << endl;
  }
};

class Polygon: public Shape{
  Point p1_;
  Point p2_;
  Point p3_;
public:
  Polygon(Point p1, Point p2, Point p3)
  :p1_(p1),
   p2_(p2),
   p3_(p3)
  {}

  void draw() const{
    cout << "<polygon points=\""<< p1_.get_x()<<","<<p1_.get_y()<<" "<<p2_.get_x()<<","<<p2_.get_y()<<" "<<p3_.get_x()<<","<<p3_.get_y()<<"\""<< get_style() << "/>" << endl;
  }
};

class Drawing{
  list<Shape*> shapes_;
public:
  void add(Shape* shape) {
    shapes_.push_back(shape);
  }

  void draw() {
    cout << "<svg xmlns=\"http://www.w3.org/2000/svg\">" << endl;

    for(list<Shape*>::const_iterator it = shapes_.begin(); it != shapes_.end(); it++) {
      (*it)->draw();
    }
    cout << "</svg>" << endl;
  }
};


int main(){

  Drawing d;
  /* Drawing Circle
  for(int i=0;i<50;i++){
    Circle* pc = new Circle(Point(i*10, i*10),i*5);
    //pc->set_stroke("red");
    ostringstream style;
    style << "stroke: red;" << "stroke-width:" << 0.1*i << ";" << "fill: rgb(" << i*2 <<"," <<i << "," << i << ");";

    pc->set_style(style.str());

    d.add(pc);
  }
  */

  /* Drawing Rectangle
  Rectangle* pr = new Rectangle(Point(100, 50), 100, 20);
  ostringstream style;
  style << "fill:blue;stroke:pink;stroke-width:5;fill-opacity:0.1;stroke-opacity:0.9";
  pr->set_style(style.str());
  d.add(pr);
  */

  /* Drawing Ellipse
  Ellipse* pe = new Ellipse(Point(100,50), 100, 50);
  ostringstream style;
  style << "fill:yellow;stroke:purple;stroke-width:2";
  pe->set_style(style.str());
  d.add(pe);
  */

  /* Drawing Line
  Line* pl = new Line(Point(50, 50), Point(200,200));
  ostringstream style;
  style << "stroke:rgb(255,0,0);stroke-width:2";
  pl->set_style(style.str());
  d.add(pl);
  */

  /* Drawing Polygon
  Polygon* pp = new Polygon(Point(200,10), Point(250,190), Point(160,210));
  ostringstream style;
  style << "fill:lime;stroke:purple;stroke-width:1";
  pp->set_style(style.str());
  d.add(pp);
  */

  Polyline* ppl = new Polyline(Point(20,20), Point(40,25), Point(60,40), Point(80,120), Point(120,140), Point(200,180));
  ostringstream style;
  style << "fill:none;stroke:black;stroke-width:3";
  ppl->set_style(style.str());
  d.add(ppl);

  d.draw();
  return 0;
}
//HW: to add rectangle, ellipse, line and etc. To see in W3schools
