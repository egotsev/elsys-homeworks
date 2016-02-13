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

class Shapes {
	
	public:
		
		virtual void draw() const = 0;
};

class Rectangle : public Shapes {
	int width_, height_;
	const char *color_;
	Point coord_;
	
	public:
		
		Rectangle(Point coord, int width=100, int height=100, const char* color="blue") : coord_(coord), width_(width), height_(height), color_(color) {}
	
		void draw() const {
			cout << "<rect x=\"" << coord_.get_x() 
			<< "\" y=\"" << coord_.get_y() 
			<< "\" width=\"" << width_ 
			<< "\" height=\"" << height_ 
			<< "\" style=\"fill:" << color_ 
			<< "\" />"
			<< endl;
		}
	
};

class Ellipse : public Shapes {
		Point coord_;
		int radius_x_, radius_y_;
		const char *color_;
		
	public:
		
		Ellipse(Point coord, int radius_x=100, int radius_y=50, const char *color="yellow") : coord_(coord), radius_x_(radius_x), radius_y_(radius_y), color_(color) {}
		
		void draw() const {
			cout << "<ellipse cx=\"" << coord_.get_x() 
			<< "\" cy=\"" << coord_.get_y() 
			<< "\" rx=\"" << radius_x_ 
			<< "\" ry=\"" << radius_y_ 
			<< "\" style=\"fill:" << color_ 
			<< "\" />"
			<< endl;
		}
};

class Line : public Shapes {
		Point coord_;
		int dest_x_, dest_y_;
		const char* color_;
		
	public:
	
		Line(Point coord, int dest_x=100, int dest_y=100, const char *color="white") : coord_(coord), dest_x_(dest_x+coord_.get_x()), dest_y_(dest_y+coord_.get_y()), color_(color) {}
		
		void draw() const {
			cout << "<line x1=\"" << coord_.get_x() 
			<< "\" y1=\"" << coord_.get_y() 
			<< "\" x2=\"" << dest_x_ 
			<< "\" y2=\"" << dest_y_ 
			<< "\" style=\"stroke:" << color_ 
			<< ";stroke-width:3\" />"
			<< endl;
		}
};

class Polygon : public Shapes {
	Point corner_1_, corner_2_, corner_3_;
	const char* color_;
	public:
	
		Polygon(Point corner_1, Point corner_2, Point corner_3 , const char* color="indigo") : corner_1_(corner_1), corner_2_(corner_2), corner_3_(corner_3), color_(color) {}
		
		void draw() const {
			cout << "<polygon points=\"" << corner_1_.get_x() << "," << corner_1_.get_y() 
			<< " " << corner_2_.get_x() << "," << corner_2_.get_y() 
			<< " " << corner_3_.get_x() << "," << corner_3_.get_y() 
			<< "\" style=\"fill:" << color_ 
			<< "\" />"
			<< endl;
		}
};

class Polyline : public Shapes {
	Point stroke_1_, stroke_2_, stroke_3_, stroke_4_, stroke_5_;
	const char* color_;
	public:
	
		Polyline(Point stroke_1, Point stroke_2, Point stroke_3, Point stroke_4, Point stroke_5, const char* color="orange") : stroke_1_(stroke_1), stroke_2_(stroke_2), stroke_3_(stroke_3), stroke_4_(stroke_4), stroke_5_(stroke_5), color_(color) {}
		
		void draw() const {
			cout << "<polyline points=\"" << stroke_1_.get_x() << "," << stroke_1_.get_y()
			<< " " << stroke_2_.get_x() << "," << stroke_2_.get_y()
			<< " " << stroke_3_.get_x() << "," << stroke_3_.get_y()
			<< " " << stroke_4_.get_x() << "," << stroke_4_.get_y()
			<< " " << stroke_5_.get_x() << "," << stroke_5_.get_y()
			<< "\" style=\"fill:lime;stroke:" << color_ << ";stroke-width:3\" />"
			<< endl;
		}
};

class Path : public Shapes {
	const char* path_start_;
	Point start_coord_;
	const char* line_to_1_;
	Point coord_1_;
	const char* line_to_2_;
	Point coord_2_;
	const char* path_end_;
	public:
	
		Path(const char* path_start, Point start_coord, const char* line_to_1, Point coord_1, const char* line_to_2, Point coord_2, const char* path_end) : path_start_(path_start), start_coord_(start_coord), line_to_1_(line_to_1), coord_1_(coord_1), line_to_2_(line_to_2), coord_2_(coord_2), path_end_(path_end) {}
		
		void draw() const {
			cout << "<path d=\"" << path_start_ << start_coord_.get_x() << " " << start_coord_.get_y()
			<< " " << line_to_1_ << coord_1_.get_x() << " " << coord_1_.get_y()
			<< " " << line_to_2_ << coord_2_.get_x() << " " << coord_2_.get_y()
			<< " " << path_end_
			<< "\" fill=\"mediumvioletred\"/>"
			<< endl;
		}
};

class Text : public Shapes {
	const char* text_;
	Point coord_;
	
	public:
		
		Text(Point coord, const char* text) : coord_(coord), text_(text) {}
		
		void draw() const {
			cout << "<text x=\"" << coord_.get_x() 
			<< "\" y=\"" << coord_.get_y() 
			<< "\" font-size=\"30px\" fill=\"navy\">" << text_ 
			<< "</text>"
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
	canvas.add(new Text(Point(30,30),"Rectangle"));
	canvas.add(new Rectangle(Point(10,40),150,50));
	canvas.add(new Rectangle(Point(10,100),50,30,"red"));
	canvas.add(new Text(Point(65,160),"Ellipse"));
	canvas.add(new Ellipse(Point(105,220)));
	canvas.add(new Text(Point(30,290),"Line"));
	canvas.add(new Line(Point(20,300)));
	canvas.add(new Text(Point(20,420),"Polygon"));
	canvas.add(new Polygon(Point(50,430),Point(150,550),Point(30,600)));
	canvas.add(new Text(Point(180,30),"Polyline"));
	canvas.add(new Polyline(Point(230,40),Point(200,70),Point(230,100),Point(200,130),Point(230,160)));
	canvas.add(new Text(Point(330,30),"Path"));
	canvas.add(new Path("M",Point(400,40),"L",Point(250,140),"L",Point(450,140),"Z"));
	canvas.draw();
	
	return 0;
}
