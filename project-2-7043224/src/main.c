#include <stdio.h>
#include <stdlib.h>

#include "argparser.h"
#include "convolution.h"
#include "derivation.h"
#include "gaussian_kernel.h"
#include "image.h"

int main(int const argc, char **const argv) {
    /**
     * Parse arguments. The parsed image file name and threshold are available
     * in the image_file_name and threshold global variables (see argparser.h).
     */
    parse_arguments(argc, argv);
    printf("Computing edges for image file %s with threshold %i\n",
           image_file_name, threshold);

    /**
     * Read Image from given file.
     *
     * If the input file is broken terminate with return value 1.
     *
     * Hint: The width and height of the image have to be accessible in the
     * scope of this function.
     */
    int width, height;
    float *image = read_image_from_file(image_file_name, &width, &height);
    if (image == NULL) {
        fprintf(stderr, "Failed to read image from file %s\n", image_file_name);
        return 1;
    }

    /**
     * Blur the image by using convolve with the given Gaussian kernel matrix
     * gaussian_k (defined in gaussian_kernel.h). The width of the matrix is
     * gaussian_w, the height is gaussian_h.
     *
     * Afterwards, write the resulting blurred image to the file out_blur.pgm.
     */

    float *blurred_image = array_init(width * height);
    convolve(blurred_image, image, width, height, gaussian_k, gaussian_w, gaussian_h);
    write_image_to_file(blurred_image,width,height, "out_blur.pgm");

    /**
     * Compute the derivation of the blurred image computed above in both x and
     * y direction.
     *
     * Afterwards, rescale both results and write them to the files out_d_x.pgm
     * and out_d_y.pgm respectively.
     */

    float* d_x = array_init(width * height);
    float* d_y = array_init(width * height);
    derivation_x_direction(d_x, blurred_image, width, height);
    derivation_y_direction(d_y, blurred_image, width, height);

    float* scaled_d_x = array_init(width * height);
    float* scaled_d_y = array_init(width * height);
    scale_image(scaled_d_x, d_x, width, height);
    scale_image(scaled_d_y, d_y, width, height);
    write_image_to_file(scaled_d_x, width, height, "out_d_x.pgm");
    write_image_to_file(scaled_d_y, width, height, "out_d_y.pgm");

    /**
     * Compute the gradient magnitude of the blurred image by using the
     * (unscaled!) derivations in x- and y-direction computed earlier.
     *
     * Afterwards, rescale the result and write it to out_gm.pgm.
     */

    float *gradient_magnitude_image = array_init(width * height);
    gradient_magnitude(gradient_magnitude_image, d_x, d_y, width, height);
    float *scaled_gmi = array_init(width * height);
    scale_image(scaled_gmi, gradient_magnitude_image, width, height);
    write_image_to_file(scaled_gmi, width, height, "out_gm.pgm");

    /**
     * Apply the threshold to the gradient magnitude.
     * Then write the result to the file out_edges.pgm.
     */

    apply_threshold(gradient_magnitude_image, width, height, threshold);
    write_image_to_file(gradient_magnitude_image, width, height, "out_edges.pgm");

    /**
     * Remember to free dynamically allocated memory when it is no longer used!
     */

    array_destroy(image);
    array_destroy(blurred_image);
    array_destroy(d_x);
    array_destroy(d_y);
    array_destroy(scaled_d_x);
    array_destroy(scaled_d_y);
    array_destroy(gradient_magnitude_image);

    return 0;
}
