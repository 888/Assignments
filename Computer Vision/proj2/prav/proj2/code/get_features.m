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

%features = image(y - feature_width/2:y + feature_width/2, x - feature_width/2:x+ feature_width/2);
blur = imgaussfilt(image, 3);
[Gmag, Gdir] = imgradient(blur);
bin = [];

bound = feature_width/2;

for k=1:size(x)
    current = [];    
    if (y(k)-feature_width/2 > 0 && x(k)-feature_width/2 > 0 ...
            && y(k)+feature_width/2-1 < size(Gmag,1) ...
            && x(k)+feature_width/2-1 < size(Gmag,2))
        
        dirmat = Gdir(y(k)-feature_width/2:y(k)+feature_width/2-1, x(k)-feature_width/2:x(k)+feature_width/2-1);
        magmat = Gmag(y(k)-feature_width/2:y(k)+feature_width/2-1, x(k)-feature_width/2:x(k)+feature_width/2-1);
        for j=1:feature_width/4:size(dirmat,1)
           for i=1:feature_width/4:size(dirmat,2)
              dirtemp = dirmat(j:j + feature_width/4-1,i:i + feature_width/4-1);
              magtemp = magmat(j:j + feature_width/4-1,i:i + feature_width/4-1);
              newBin = zeros(1,8);
              for t=1:size(dirtemp,1)
                  for s=1:size(dirtemp,2)
                      mag = magtemp(t,s);
                      if mag > .7
                          mag = .7;
                      end
                      
                      if dirtemp(t,s) >= 0 & dirtemp(t,s) < 45
                          newBin(1,1) = newBin(1,1) + mag;
                      elseif dirtemp(t,s) >= 45 & dirtemp(t,s) < 90
                          newBin(1,2) = newBin(1,2) + mag;
                      elseif dirtemp(t,s) >= 90 & dirtemp(t,s) < 135
                          newBin(1,3) = newBin(1,3) + mag;
                      elseif dirtemp(t,s) >= 135 & dirtemp(t,s) < 180
                          newBin(1,4) = newBin(1,4) + mag;
                      elseif dirtemp(t,s) >= -180 & dirtemp(t,s) < -135
                          newBin(1,5) = newBin(1,5) + mag;
                      elseif dirtemp(t,s) >= -135 & dirtemp(t,s) < -90
                          newBin(1,6) = newBin(1,6) + mag;
                      elseif dirtemp(t,s) >= -90 & dirtemp(t,s) < -45
                          newBin(1,7) = newBin(1,7) + mag;
                      else
                          newBin(1,8) = newBin(1,8) + mag;
                      end
                  end
              end

              current = [current,newBin];
           end
        end
    end
    current = current/norm(current);
    current = current.^.2;
    current = current/norm(current);
    bin = [bin;current];
end


features = bin;












