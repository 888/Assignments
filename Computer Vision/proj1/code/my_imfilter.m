function output = my_imfilter(image, filter)
% This function is intended to behave like the built in function imfilter()
% See 'help imfilter' or 'help conv2'. While terms like "filtering" and
% "convolution" might be used interchangeably, and they are indeed nearly
% the same thing, there is a difference:
% from 'help filter2'
%    2-D correlation is related to 2-D convolution by a 180 degree rotation
%    of the filter matrix.

% Your function should work for color images. Simply filter each color
% channel independently.

% Your function should work for filters of any width and height
% combination, as long as the width and height are odd (e.g. 1, 7, 9). This
% restriction makes it unambigious which pixel in the filter is the center
% pixel.

% Boundary handling can be tricky. The filter can't be centered on pixels
% at the image boundary without parts of the filter being out of bounds. If
% you look at 'help conv2' and 'help imfilter' you see that they have
% several options to deal with boundaries. You should simply recreate the
% default behavior of imfilter -- pad the input image with zeros, and
% return a filtered image which matches the input resolution. A better
% approach is to mirror the image content over the boundaries for padding.

% % Uncomment if you want to simply call imfilter so you can see the desired
% % behavior. When you write your actual solution, you can't use imfilter,
% % filter2, conv2, etc. Simply loop over all the pixels and do the actual
% % computation. It might be slow.
% output = imfilter(image, filter);


%%%%%%%%%%%%%%%%
pad_image = padarray(image, floor(size(filter) / 2), 'symmetric', 'both'); 

image_size = size(image);
image_x = image_size(1);
image_y = image_size(2);
filter_size = size(filter);
filter_x = filter_size(1);
filter_y = filter_size(2);
new_image = zeros(image_x, image_y);

if(mod(filter_x, 2) == 0)
    return;
end
if(mod(filter_y, 2) == 0)
    return;
end

for c = 1:1:size(image,3)
    for j = 1:1:image_y
        for i = 1:1:image_x
            sum = 0;
            for l = 1:1:filter_y
                for k = 1:1:filter_x
                    sum = sum + pad_image(i + k - 1, j + l - 1, c)*filter(k, l);
                end
            end
            new_image(i, j, c) = sum;
        end
    end
end
output = new_image;
%%%%%%%%%%%%%%%%