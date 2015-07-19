#include <string.h>
#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <X11/extensions/XTest.h>

void SendClick(int button, int down) {
    Display *display = XOpenDisplay(NULL);
    XEvent event;

    if(display == NULL)
    {
        return;
    }

    memset(&event, 0, sizeof(event));

    event.xbutton.button = button;
    event.xbutton.same_screen = True;
    event.xbutton.subwindow = DefaultRootWindow (display);

    while (event.xbutton.subwindow)
    {
      event.xbutton.window = event.xbutton.subwindow;
      XQueryPointer (display, event.xbutton.window,
             &event.xbutton.root, &event.xbutton.subwindow,
             &event.xbutton.x_root, &event.xbutton.y_root,
             &event.xbutton.x, &event.xbutton.y,
             &event.xbutton.state);
    }

    event.type = down ? ButtonPress : ButtonRelease;

    XSendEvent(display, PointerWindow, True, down ? ButtonPressMask : ButtonReleaseMask, &event); 

    XFlush(display);

    XCloseDisplay(display);
}



void SendClick2(int button, Bool down) {
    Display *display = XOpenDisplay(NULL);
    XTestFakeButtonEvent(display, button, down, CurrentTime);
    XFlush(display);
    XCloseDisplay(display);
}


int main(void)
{



	return 0;
}