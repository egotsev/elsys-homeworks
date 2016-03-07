#include<iostream>
#include<list>
#include<string>
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

class Shapes {
	
	public:
		
		virtual void draw() const = 0;
};

class Rectangle : public Shapes {
	int width_, height_;
	Point coord_;
	
	public:
		
		Rectangle(Point coord, int width=100, int height=100) : coord_(coord), width_(width), height_(height) {}
	
		void draw() const {
			cout << "<rect x=\"" << coord_.get_x() 
			<< "\" y=\"" << coord_.get_y() 
			<< "\" width=\"" << width_ 
			<< "\" height=\"" << height_ 
			<< "\" style=\"fill:black\" />"
			<< endl;
		}
	
};

class Ellipse : public Shapes {
		Point coord_;
		int radius_x_, radius_y_;
		
	public:
		
		Ellipse(Point coord, int radius_x=100, int radius_y=50) : coord_(coord), radius_x_(radius_x), radius_y_(radius_y) {}
		
		void draw() const {
			cout << "<ellipse cx=\"" << coord_.get_x() 
			<< "\" cy=\"" << coord_.get_y() 
			<< "\" rx=\"" << radius_x_ 
			<< "\" ry=\"" << radius_y_ 
			<< "\" style=\"fill:black\" />"
			<< endl;
		}
};

class Line : public Shapes {
		Point coord_;
		int dest_x_, dest_y_;
		
	public:
	
		Line(Point coord, int dest_x=100, int dest_y=100) : coord_(coord), dest_x_(dest_x+coord_.get_x()), dest_y_(dest_y+coord_.get_y()) {}
		
		void draw() const {
			cout << "<line x1=\"" << coord_.get_x() 
			<< "\" y1=\"" << coord_.get_y() 
			<< "\" x2=\"" << dest_x_ 
			<< "\" y2=\"" << dest_y_ 
			<< "\" style=\"stroke:black;stroke-width:3\" />"
			<< endl;
		}
};

class Polygon : public Shapes {
	list<Point> coord_;
	public:
	
		Polygon(list<Point> coord) : coord_(coord) {}
		
		void draw() const {
			cout << "<polygon points=\"";
			for(list<Point>::const_iterator it = coord_.begin(); it != coord_.end(); it++) {
				cout << it->get_x() << "," << it->get_y() << " ";
			}
			cout << "\" style=\"fill:black\" />"
			<< endl;
		}
};

class Polyline : public Shapes {
	list<Point> coord_;
	public:
	
		Polyline(list<Point> coord) : coord_(coord) {}
		
		void draw() const {
			cout << "<polyline points=\"";
			for(list<Point>::const_iterator it = coord_.begin(); it != coord_.end(); it++) {
				cout << it->get_x() << "," << it->get_y() << " ";
			}
			cout << "\" style=\"stroke:black;stroke-width:3\" />"
			<< endl;
		}
};

class Path : public Shapes {
	list<Point> coord_;
	list<string> path_;
	public:
	
		Path(list<string> path, list<Point> coord) : path_(path), coord_(coord) {}
		
		void draw() const {
			cout << "<path d=\"";
			list<Point>::const_iterator it2 = coord_.begin();
			for(list<string>::const_iterator it = path_.begin(); it != path_.end(); it++) {
				if(it2 != coord_.end()) {
					cout << *it << it2->get_x() << " " << it2->get_y() << " ";
					it2++;
				}
				else {
					cout << *it;
				}
			}
			cout << "\" />"
			<< endl;
		}
};

class CompositeFigures : public Shapes {
	list<Shapes*> fig_list_;
	
	public:
		~CompositeFigures() {
			for(list<Shapes*>::iterator it = fig_list_.begin(); it != fig_list_.end(); it++) {
				delete *it;
			}
		}
		
		void add(Shapes* fig) {
			fig_list_.push_back(fig);
		}
	
		void draw() const {
			for(list<Shapes*>::const_iterator it = fig_list_.begin(); it != fig_list_.end(); it++) {
				(*it)->draw();
			}
		}
};

class Canvas : public CompositeFigures {
	int width_, height_;
	
	public:
	
		Canvas(int width=200, int height=200) : width_(width), height_(height) {}
		
		void draw() const {
			cout << "<svg width=\"" << width_ 
			<< "\" height=\"" << height_ << "\">"
			<< endl;
			
			CompositeFigures::draw();
			
			cout << "</svg>" << endl;
		}
};

int main() {
	Canvas canvas(800,800);
	
	canvas.add(new Rectangle(Point(10,40),150,50));
	canvas.add(new Rectangle(Point(10,100),50,30));
	
	canvas.add(new Ellipse(Point(105,220)));
	
	canvas.add(new Line(Point(20,300)));
	
	list<Point> polygon;
	polygon.push_back(Point(50,430));
	polygon.push_back(Point(150,550));
	polygon.push_back(Point(30,600));
	canvas.add(new Polygon(polygon));
	
	list<Point> polyline;
	polyline.push_back(Point(230,40));
	polyline.push_back(Point(200,70));
	polyline.push_back(Point(230,100));
	polyline.push_back(Point(200,130));
	polyline.push_back(Point(230,160));
	canvas.add(new Polyline(polyline));
	
	list<string> path;
	list<Point> path_coord;
	path.push_back("M");
	path_coord.push_back(Point(400,40));
	path.push_back("L");
	path_coord.push_back(Point(250,140));
	path.push_back("L");
	path_coord.push_back(Point(450,140));
	path.push_back("Z");
	canvas.add(new Path(path,path_coord));
	canvas.draw();
	
	return 0;
}
