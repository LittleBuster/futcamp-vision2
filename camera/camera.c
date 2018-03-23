#include <stdio.h>
#include <string.h>
#include <stdbool.h>

#include <opencv/cv.h>
#include <opencv/highgui.h>


#define MAX_CAM_COUNT 10


static unsigned GetCamCount(void)
{
    CvCapture *cam;
    unsigned count = 0;

    for (unsigned i = 0; i < MAX_CAM_COUNT; i++) {
        cam = cvCreateCameraCapture(i);
        if (cam == NULL)
            break;
        cvReleaseCapture(&cam);
        count++;
    }

    return count;
}

static bool GetCamPhoto(unsigned camera, const char *filename)
{
    CvCapture *capture;
    IplImage *frame;

    capture = cvCreateCameraCapture(camera);
    if (capture == NULL)        
        return false;

    frame = cvQueryFrame(capture);
    if (frame == NULL) {
        cvReleaseCapture(&capture);
        return false;
    }
    cvSaveImage(filename, frame, 0);

    cvReleaseImage(&frame);
    cvReleaseCapture(&capture);

    return true;
}


int main(int argc, char const *argv[])
{
    if (argc < 2) {
        printf("Error: Incorrect params count\n");
        return -1;
    }

    if (!strcmp(argv[1], "-c")) {
        unsigned count;

        count = GetCamCount();
        printf("Count: %u\n", count);

        return 0;
    }

    if (!strcmp(argv[1], "-g")) {
        unsigned camera;
        const char *filename;

        if (argc < 4) {
            printf("Error: Incorrect params count\n");
            return -1;
        }

        sscanf(argv[2], "%u", &camera);
        filename = argv[3];

        if (!GetCamPhoto(camera, filename)) {
            printf("Error: Fail get photo from cam\n");
            return -1;
        }
    }
    return 0;
}