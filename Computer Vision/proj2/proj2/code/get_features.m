% Local Feature Stencil Code
% CS 4476 / 6476: Computer Vision, Georgia Tech
% Written by James Hays

% Returns a set of feature descriptors for a given set of interest points. 

% 'image' can be grayscale or color, your choice.
% 'x' and 'y' are nx1 vectors of x and y coordinates of interest points.
%   The local features should be centered at x and y.
% 'feature_width', in pixels, is the local feature width. You can assume
%   that feature_width will be a multiple of 4 (i.e. every cell of your
%   local SIFT-like feature will have an integer width and height).
% If you want to detect and describe features at multiple scales or
% particular orientations you can add input arguments.

% 'features' is the array of computed features. It should have the
%   following size: [length(x) x feature dimensionality] (e.g. 128 for
%   standard SIFT)

function [features] = get_features(image, x, y, feature_width)

% To start with, you might want to simply use normalized patches as your
% local feature. This is very simple to code and works OK. However, to get
% full credit you will need to implement the more effective SIFT descriptor
% (See Szeliski 4.1.2 or the original publications at
% http://www.cs.ubc.ca/~lowe/keypoints/)

% Your implementation does not need to exactly match the SIFT reference.
% Here are the key properties your (baseline) descriptor should have:
%  (1) a 4x4 grid of cells, each feature_width/4. 'cell' in this context
%    nothing to do with the Matlab data structue of cell(). It is simply
%    the terminology used in the feature literature to describe the spatial
%    bins where gradient distributions will be described.
%  (2) each cell should have a histogram of the local distribution of
%    gradients in 8 orientations. Appending these histograms together will
%    give you 4x4 x 8 = 128 dimensions.
%  (3) Each feature vector should be normalized to unit length
%
% You do not need to perform the interpolation in which each gradient
% measurement contributes to multiple orientation bins in multiple cells
% As described in Szeliski, a single gradient measurement creates a
% weighted contribution to the 4 nearest cells and the 2 nearest
% orientation bins within each cell, for 8 total contributions. This type
% of interpolation probably will help, though.

% You do not have to explicitly compute the gradient orientation at each
% pixel (although you are free to do so). You can instead filter with
% oriented filters (e.g. a filter that responds to edges with a specific
% orientation). All of your SIFT-like feature can be constructed entirely
% from filtering fairly quickly in this way.

% You do not need to do the normalize -> threshold -> normalize again
% operation as detailed in Szeliski and the SIFT paper. It can help, though.

% Another simple trick which can help is to raise each element of the final
% feature vector to some power that is less than one.

%Placeholder that you can delete. Empty features.
[Gmag, Gdir] = imgradient(imgaussfilt(image, 3));
bin = [];
cap = 0.7;
bound = feature_width/2;
half_bound = feature_width/4;

for c=1:size(x)
    current = [];    
    if (y(c)-bound >= 1 && x(c)-bound >= 1 && y(c)+bound-1 < size(Gmag,1) && x(c)+bound-1 < size(Gmag,2))
        magnitude = Gmag(y(c)-bound:y(c)+bound-1, x(c)-bound:x(c)+bound-1);        
        direction = Gdir(y(c)-bound:y(c)+bound-1, x(c)-bound:x(c)+bound-1);
        a = 1;
        while a<=size(direction,1)
            b = 1;
            while b<=size(direction,2)
                newBin = zeros(1,8);
                magnitude1 = magnitude(a:a + half_bound-1,b:b + half_bound-1);
                direction1 = direction(a:a + half_bound-1,b:b + half_bound-1);
                p = 1;
                while p<=size(direction1,1)
                    s = 1;
                    while s<=size(direction1,2)
                        mag = magnitude1(p,s);
                        if mag > cap
                            mag = cap;
                        end
                        if direction1(p,s) > -1 && direction1(p,s) <= 44
                            newBin(1,1) = newBin(1,1) + mag;
                        elseif direction1(p,s) > 44 && direction1(p,s) <= 89
                            newBin(1,2) = newBin(1,2) + mag;
                        elseif direction1(p,s) > 89 && direction1(p,s) <= 134
                            newBin(1,3) = newBin(1,3) + mag;
                        elseif direction1(p,s) > 134 && direction1(p,s) <= 181
                            newBin(1,4) = newBin(1,4) + mag;
                        elseif direction1(p,s) > -181 && direction1(p,s) <= -134
                            newBin(1,5) = newBin(1,5) + mag;
                        elseif direction1(p,s) > -136 && direction1(p,s) <= -91
                            newBin(1,6) = newBin(1,6) + mag;
                        elseif direction1(p,s) > -91 && direction1(p,s) <= -46
                            newBin(1,7) = newBin(1,7) + mag;
                        else
                            newBin(1,8) = newBin(1,8) + mag;
                        end
                        s=s+1;
                    end
                    p=p+1;
                end
                current = [current,newBin];
                b=b+half_bound;
            end
            a=a+half_bound;
        end
    end
    current = current/norm(current);
    current = current.^.2;
    current = current/norm(current);
    bin = [bin;current];
end

features = bin;