% RANSAC Stencil Code
% CS 4476 / 6476: Computer Vision, Georgia Tech
% Written by Henry Hu

% Find the best fundamental matrix using RANSAC on potentially matching
% points

% 'matches_a' and 'matches_b' are the Nx2 coordinates of the possibly
% matching points from pic_a and pic_b. Each row is a correspondence (e.g.
% row 42 of matches_a is a point that corresponds to row 42 of matches_b.

% 'Best_Fmatrix' is the 3x3 fundamental matrix
% 'inliers_a' and 'inliers_b' are the Mx2 corresponding points (some subset
% of 'matches_a' and 'matches_b') that are inliers with respect to
% Best_Fmatrix.

% For this section, use RANSAC to find the best fundamental matrix by
% randomly sample interest points. You would reuse
% estimate_fundamental_matrix() from part 2 of this assignment.

% If you are trying to produce an uncluttered visualization of epipolar
% lines, you may want to return no more than 30 points for either left or
% right images.

function [ Best_Fmatrix, inliers_a, inliers_b] = ransac_fundamental_matrix(matches_a, matches_b)

%%%%%%%%%%%%%%%%
% Your code here
%%%%%%%%%%%%%%%%
best = zeros(3);
in_a = [];
in_b = [];

for i=1:10000
    temp_a = [];
    temp_b = [];
    input_a = matches_a(randsample(size(matches_a, 1), 8),:);
    input_b = matches_b(randsample(size(matches_b, 1), 8),:);
    fund_mat = estimate_fundamental_matrix(input_a, input_b);
    for j=1:size(matches_b, 1)
        a = [matches_a(j,:)];
        b = [matches_b(j,:)];
        a = [a, 1];
        b = [b, 1];
        if (a * fund_mat * b' > -0.04 && a * fund_mat * b' < 0.04) 
            temp_a = [temp_a; matches_a(j,:)];
            temp_b = [temp_b; matches_b(j,:)];
        end
    end
    if size(temp_a,1) > size(in_a,1)
        best = fund_mat;
        in_a = temp_a;
        in_b = temp_b;
    end
end
inliers_a = in_a;
inliers_b = in_b;
Best_Fmatrix = best;
% Your ransac loop should contain a call to 'estimate_fundamental_matrix()'
% that you wrote for part II.

%placeholders, you can delete all of this
end