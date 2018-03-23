CC=gcc
CFLAGS=-std=gnu99 -O2
CLIBS=-lm -lopencv_core -lopencv_highgui -lopencv_imgproc

all: camera

camera: camera.o
	$(CC) camera.o $(CFLAGS) $(CLIBS) -o camera

camera.o: camera.c
	$(CC) -c camera.c $(CFLAGS) $(CLIBS)

clean:
	rm -rf *.o camera
