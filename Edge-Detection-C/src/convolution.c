#include "convolution.h"

#include <stdlib.h>

#include "image.h"

void convolve(float *result, const float *img, int w, int h,
              const float *matrix, int w_m, int h_m) {
    int half_w_m = w_m /2;
    int half_h_m = h_m /2;

    for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
            float sum = 0.0;

            for (int my = 0; my < h_m; my++) {
                for (int mx = 0; mx < w_m; mx++) {
                    int img_x = x + mx - half_w_m;
                    int img_y = y + my - half_h_m;
                    float pixel_value = get_pixel_value(img, w, h, img_x, img_y);
                    sum += pixel_value * matrix[my * w_m + mx];
                }
            }
            result[y * w + x] = sum;
        }
    }
}
