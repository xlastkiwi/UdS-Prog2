#include "image.h"

#include <assert.h>
#include <math.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void apply_threshold(float *img, int w, int h, int T) {
    for (int i = 0; i < w * h; i++) {
        if (img[i] > T) {
            img[i] = 255;
        } else {
            img[i] = 0;
        }
    }
    // TODO: Implement me!


}

void scale_image(float *result, const float *img, int w, int h) {
    float min = img[0];
    float max = img[0];

    for (int i = 1; i < w * h; i++) {
        if (img[i] < min) {
            min = img[i];
        }
        if (img[i] > max) {
            max = img[i];
        }
    }

    if (max == min) {
        for (int i = 0; i < w * h; i++) {
            result[i] = 0;
        }
    } else {
        for (int i = 0; i < w * h; i++) {
            result[i] = ((img[i] - min) / (max - min)) * 255;
        }
    }
}

float get_pixel_value(const float *img, int w, int h, int x, int y) {
    if (x < 0) {
        x = -x-1;
    } else if (x >= w) {
        x = 2 * w - x - 1;
    }

    if (y < 0) {
        y = -y-1;
    } else if (y >= h) {
        y = 2 * h - y - 1;
    }

    return img[y * w + x];
}

float *array_init(int size) {
    return (float *)malloc(size * sizeof(float));
}

void array_destroy(float *m) {
    if (m != NULL) {
        free(m);
    }
}

float *read_image_from_file(const char *filename, int *w, int *h) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        return NULL;
    }

    char format[3];
    if (fscanf(file, "%2s", format) != 1 || strcmp(format, "P2") != 0) {
        fclose(file);
        return NULL;
    }

    if (fscanf(file, "%d %d", w, h) != 2 || *w <= 0 || *h <= 0) {
        fclose(file);
        return NULL;
    }

    int max_val;
    if (fscanf(file, "%d", &max_val) != 1 || max_val != 255) {
        fclose(file);
        return NULL;
    }

    int size = (*w) * (*h);
    float* img = array_init(size);
    if (!img) {
        fclose(file);
        return NULL;
    }

    for (int i = 0; i < size; i++) {
        int pixel;
        if (fscanf(file, "%d", &pixel) != 1 || pixel < 0 || pixel > 255) {
            array_destroy(img);
            fclose(file);
            return NULL;
        }
        img[i] = (float)pixel;
    }

    int extra_pixel;
    if (fscanf(file, "%d", &extra_pixel) == 1) {
        array_destroy(img);
        fclose(file);
        return NULL;
    }

    fclose(file);
    return img;
}

void write_image_to_file(const float *img, int w, int h, const char *filename) {
    FILE* file = fopen(filename, "w");
    if (!file) {
        return;
    }

    fprintf(file, "P2\n%d %d\n255\n", w, h);

    for (int i = 0; i < w * h; i++) {
        if (i > 0 && i % w == 0) {
            fprintf(file, "\n");
        }
        fprintf(file, "%d ", (int)img[i]);
    }
    fclose(file);
}
