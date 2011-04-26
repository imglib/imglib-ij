package net.imglib2.nearestneighbor;
/**
 * Copyright (c) 2010, Stephan Preibisch & Stephan Saalfeld
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.  Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials
 * provided with the distribution.  Neither the name of the Fiji project nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import net.imglib2.Cursor;
import net.imglib2.RealPoint;
import net.imglib2.collection.RealPointSampleList;
import net.imglib2.exception.ImgLibException;
import net.imglib2.img.imageplus.ImagePlusImg;
import net.imglib2.img.imageplus.ImagePlusImgFactory;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.ImageStack;

public class KNearestNeighborSearchBehavior
{
	final static private long[] size = new long[]{ 200, 164 };
	final static private double[][] coordinates =
		new double[][]{ { 51, 52 }, { 41, 36 }, { 55, 64 }, { 175, 18 }, { 101, 97 }, { 117, 126 }, { 190, 66 }, { 12, 148 }, { 187, 139 }, { 132, 143 }, { 1, 19 }, { 192, 160 }, { 28, 21 }, { 25, 31 }, { 71, 143 }, { 129, 61 }, { 101, 25 }, { 103, 91 }, { 152, 51 }, { 10, 24 }, { 196, 122 }, { 99, 162 }, { 118, 0 }, { 12, 139 }, { 150, 100 }, { 174, 140 }, { 54, 24 }, { 180, 115 }, { 158, 127 }, { 194, 141 }, { 94, 163 }, { 153, 158 }, { 183, 107 }, { 170, 1 }, { 190, 27 }, { 76, 25 }, { 188, 94 }, { 49, 160 }, { 152, 107 }, { 3, 96 }, { 32, 88 }, { 75, 12 }, { 9, 95 }, { 198, 36 }, { 87, 75 }, { 153, 67 }, { 151, 109 }, { 115, 138 }, { 174, 149 }, { 150, 128 }, { 105, 57 }, { 96, 9 }, { 106, 85 }, { 170, 118 }, { 44, 13 }, { 132, 134 }, { 116, 20 }, { 186, 79 }, { 84, 39 }, { 104, 109 }, { 12, 7 }, { 13, 4 }, { 175, 133 }, { 175, 73 }, { 88, 130 }, { 59, 38 }, { 23, 47 }, { 126, 143 }, { 99, 115 }, { 38, 138 }, { 133, 4 }, { 182, 44 }, { 127, 136 }, { 138, 41 }, { 56, 160 }, { 7, 153 }, { 38, 96 }, { 198, 145 }, { 54, 162 }, { 5, 14 }, { 26, 126 }, { 97, 126 }, { 184, 155 }, { 157, 151 }, { 58, 76 }, { 128, 129 }, { 167, 125 }, { 37, 144 }, { 14, 113 }, { 148, 67 }, { 187, 141 }, { 49, 158 }, { 23, 162 }, { 70, 82 }, { 72, 133 }, { 146, 135 }, { 50, 32 }, { 69, 19 }, { 88, 78 }, { 79, 129 }, { 84, 156 }, { 180, 121 }, { 38, 47 }, { 165, 23 }, { 52, 101 }, { 183, 72 }, { 7, 159 }, { 32, 6 }, { 141, 69 }, { 35, 83 }, { 16, 62 }, { 51, 44 }, { 163, 137 }, { 92, 88 }, { 88, 133 }, { 51, 23 }, { 183, 49 }, { 125, 80 }, { 58, 120 }, { 109, 117 }, { 140, 117 }, { 128, 106 }, { 114, 74 }, { 179, 65 }, { 17, 45 }, { 36, 116 }, { 121, 131 }, { 185, 74 }, { 181, 156 }, { 24, 56 }, { 126, 102 }, { 172, 153 }, { 84, 146 }, { 161, 140 }, { 136, 21 }, { 181, 130 }, { 145, 160 }, { 43, 112 }, { 129, 162 }, { 133, 114 }, { 99, 131 }, { 101, 144 }, { 67, 31 }, { 24, 54 }, { 113, 45 }, { 104, 135 }, { 180, 46 }, { 156, 86 }, { 88, 66 }, { 6, 20 }, { 19, 132 }, { 9, 94 }, { 129, 147 }, { 156, 108 }, { 90, 38 }, { 160, 92 }, { 5, 10 }, { 114, 97 }, { 100, 3 }, { 37, 31 }, { 163, 139 }, { 135, 68 }, { 109, 111 }, { 150, 154 }, { 198, 108 }, { 127, 59 }, { 199, 158 }, { 171, 134 }, { 161, 109 }, { 87, 28 }, { 137, 37 }, { 54, 115 }, { 188, 36 }, { 157, 0 }, { 111, 88 }, { 114, 23 }, { 38, 19 }, { 178, 84 }, { 39, 113 }, { 198, 25 }, { 130, 84 }, { 97, 86 }, { 63, 47 }, { 196, 115 }, { 79, 16 }, { 138, 84 }, { 116, 95 }, { 147, 107 }, { 155, 68 }, { 66, 9 }, { 125, 141 }, { 134, 139 }, { 44, 86 }, { 68, 2 }, { 88, 87 }, { 177, 139 }, { 152, 77 }, { 113, 23 }, { 54, 71 }, { 47, 52 }, { 41, 5 }, { 105, 159 }, { 143, 12 }, { 158, 78 }, { 23, 46 }, { 23, 88 }, { 81, 81 }, { 184, 101 }, { 195, 115 }, { 114, 108 }, { 136, 44 }, { 190, 23 }, { 15, 63 }, { 191, 85 }, { 94, 136 }, { 173, 20 }, { 40, 159 }, { 74, 86 }, { 10, 43 }, { 58, 50 }, { 134, 97 }, { 84, 51 }, { 162, 108 }, { 95, 20 }, { 40, 138 }, { 51, 60 }, { 75, 28 }, { 62, 87 }, { 75, 45 }, { 47, 42 }, { 45, 145 }, { 26, 55 }, { 34, 45 }, { 168, 32 }, { 137, 38 }, { 155, 78 }, { 4, 158 }, { 91, 83 }, { 172, 156 }, { 160, 85 }, { 194, 0 }, { 196, 82 }, { 99, 160 }, { 188, 56 }, { 183, 118 }, { 27, 86 }, { 31, 53 }, { 75, 118 }, { 48, 80 }, { 49, 87 }, { 88, 6 }, { 3, 26 }, { 164, 44 }, { 41, 12 }, { 187, 38 }, { 6, 26 }, { 51, 161 }, { 97, 50 }, { 182, 74 }, { 164, 104 }, { 56, 54 }, { 184, 109 }, { 168, 58 }, { 83, 96 }, { 89, 35 }, { 95, 45 }, { 196, 75 }, { 72, 130 }, { 99, 136 }, { 122, 82 }, { 140, 150 }, { 53, 49 }, { 32, 39 }, { 190, 60 }, { 111, 95 }, { 163, 130 }, { 146, 71 }, { 170, 111 }, { 173, 30 }, { 70, 145 }, { 167, 87 }, { 168, 87 }, { 87, 110 }, { 62, 151 }, { 61, 34 }, { 50, 116 }, { 154, 148 }, { 130, 110 }, { 36, 129 }, { 135, 47 }, { 106, 3 }, { 177, 58 }, { 53, 27 }, { 145, 87 }, { 121, 41 }, { 143, 75 }, { 150, 104 }, { 23, 9 }, { 120, 68 }, { 5, 97 }, { 133, 88 }, { 56, 96 }, { 144, 103 }, { 45, 45 }, { 168, 4 }, { 195, 99 }, { 4, 76 }, { 168, 10 }, { 53, 66 }, { 144, 104 }, { 176, 133 }, { 36, 132 }, { 78, 82 }, { 143, 73 }, { 146, 5 }, { 112, 121 }, { 135, 76 }, { 92, 90 }, { 44, 10 }, { 132, 119 }, { 124, 95 }, { 175, 25 }, { 198, 121 }, { 60, 148 }, { 14, 136 }, { 78, 121 }, { 182, 136 }, { 147, 113 }, { 4, 27 }, { 19, 103 }, { 57, 78 }, { 18, 3 }, { 108, 148 }, { 114, 51 }, { 153, 70 }, { 80, 72 }, { 73, 35 }, { 72, 134 }, { 142, 0 }, { 112, 15 }, { 99, 109 }, { 23, 93 }, { 7, 102 }, { 72, 163 }, { 76, 136 }, { 167, 9 }, { 144, 96 }, { 30, 159 }, { 47, 54 }, { 147, 12 }, { 185, 106 }, { 4, 131 }, { 1, 78 }, { 49, 145 }, { 154, 76 }, { 23, 42 }, { 135, 60 }, { 102, 61 }, { 74, 105 }, { 180, 38 }, { 161, 96 }, { 102, 124 }, { 63, 29 }, { 39, 11 }, { 186, 22 }, { 58, 110 }, { 38, 78 }, { 162, 17 }, { 179, 78 }, { 166, 146 }, { 123, 143 }, { 99, 13 }, { 171, 36 }, { 10, 57 }, { 175, 13 }, { 64, 124 }, { 83, 35 }, { 56, 144 }, { 160, 163 }, { 88, 119 }, { 22, 128 }, { 82, 157 }, { 70, 3 }, { 10, 7 }, { 10, 35 }, { 109, 36 }, { 23, 105 }, { 168, 142 }, { 196, 7 }, { 80, 91 }, { 163, 127 }, { 28, 103 }, { 138, 44 }, { 163, 104 }, { 18, 86 }, { 50, 39 }, { 92, 22 }, { 80, 112 }, { 105, 107 }, { 179, 31 }, { 3, 149 }, { 134, 144 }, { 84, 71 }, { 72, 2 }, { 49, 21 }, { 97, 146 }, { 18, 17 }, { 75, 84 }, { 1, 139 }, { 107, 14 }, { 52, 12 }, { 162, 113 }, { 104, 87 }, { 117, 146 }, { 68, 103 }, { 181, 34 }, { 16, 22 }, { 124, 107 }, { 25, 124 }, { 141, 58 }, { 138, 125 }, { 62, 105 }, { 7, 8 }, { 197, 37 }, { 73, 63 }, { 12, 150 }, { 105, 148 }, { 122, 20 }, { 139, 27 }, { 112, 75 }, { 121, 59 }, { 101, 99 }, { 184, 23 }, { 22, 25 }, { 187, 13 }, { 173, 21 }, { 83, 89 }, { 44, 136 }, { 184, 113 }, { 70, 156 }, { 6, 10 }, { 171, 91 }, { 166, 55 }, { 70, 149 }, { 24, 133 }, { 69, 26 }, { 2, 26 }, { 173, 75 }, { 184, 12 }, { 71, 14 }, { 72, 66 }, { 162, 37 }, { 184, 35 }, { 126, 120 }, { 190, 147 }, { 170, 43 }, { 188, 10 }, { 91, 70 }, { 127, 12 }, { 178, 48 }, { 186, 123 }, { 81, 147 }, { 84, 64 }, { 81, 78 }, { 170, 57 }, { 75, 95 }, { 2, 56 }, { 49, 56 }, { 165, 50 }, { 48, 24 }, { 72, 75 }, { 69, 78 }, { 160, 141 }, { 73, 25 }, { 95, 132 }, { 58, 37 }, { 71, 120 }, { 159, 123 }, { 98, 54 }, { 95, 48 }, { 156, 78 }, { 135, 83 }, { 77, 63 }, { 55, 23 }, { 111, 17 }, { 151, 108 }, { 96, 84 }, { 19, 163 }, { 100, 17 }, { 16, 98 }, { 11, 82 }, { 106, 155 }, { 130, 111 }, { 123, 13 }, { 105, 20 }, { 58, 80 }, { 145, 155 }, { 158, 90 }, { 15, 154 }, { 134, 98 }, { 17, 162 }, { 46, 121 }, { 171, 147 }, { 168, 148 }, { 78, 96 }, { 196, 73 }, { 135, 63 }, { 109, 31 }, { 128, 86 }, { 58, 89 }, { 61, 13 }, { 78, 152 }, { 116, 84 }, { 29, 100 }, { 186, 98 }, { 35, 64 }, { 54, 72 }, { 114, 126 }, { 21, 107 }, { 63, 43 }, { 25, 52 }, { 186, 16 }, { 126, 79 }, { 129, 113 }, { 171, 108 }, { 112, 48 }, { 161, 14 }, { 30, 54 }, { 124, 5 }, { 167, 86 }, { 138, 114 }, { 18, 63 }, { 84, 155 }, { 182, 139 }, { 193, 97 }, { 62, 78 }, { 1, 51 }, { 85, 29 }, { 189, 137 }, { 88, 61 }, { 113, 113 }, { 79, 152 }, { 18, 118 }, { 40, 31 }, { 170, 114 }, { 16, 103 }, { 173, 23 }, { 22, 29 }, { 39, 30 }, { 198, 20 }, { 132, 161 }, { 151, 133 }, { 59, 48 }, { 154, 43 }, { 64, 153 }, { 191, 43 }, { 76, 120 }, { 78, 39 }, { 140, 110 }, { 86, 150 }, { 19, 68 }, { 133, 7 }, { 5, 8 }, { 53, 67 }, { 153, 75 }, { 119, 134 }, { 41, 38 }, { 79, 34 }, { 89, 123 }, { 148, 47 }, { 74, 108 }, { 122, 106 }, { 42, 83 }, { 94, 105 }, { 178, 33 }, { 133, 137 }, { 180, 77 }, { 25, 146 }, { 22, 6 }, { 162, 65 }, { 15, 9 }, { 77, 136 }, { 13, 40 }, { 19, 95 }, { 113, 143 }, { 112, 103 }, { 0, 74 }, { 85, 147 }, { 70, 157 }, { 125, 144 }, { 81, 84 }, { 2, 138 }, { 119, 62 }, { 65, 121 }, { 141, 148 }, { 144, 22 }, { 22, 112 }, { 2, 82 }, { 68, 41 }, { 109, 61 }, { 54, 126 }, { 177, 77 }, { 21, 102 }, { 140, 102 }, { 6, 94 }, { 69, 10 }, { 39, 2 }, { 146, 8 }, { 48, 151 }, { 142, 79 }, { 149, 107 }, { 129, 80 }, { 4, 157 }, { 49, 142 }, { 106, 40 }, { 164, 115 }, { 51, 28 }, { 55, 128 }, { 99, 143 }, { 25, 2 }, { 70, 138 }, { 1, 68 }, { 89, 56 }, { 32, 9 }, { 115, 17 }, { 109, 62 }, { 171, 116 }, { 188, 17 }, { 175, 108 }, { 10, 9 }, { 198, 156 }, { 79, 82 }, { 35, 89 }, { 8, 150 }, { 38, 125 }, { 39, 105 }, { 96, 61 }, { 82, 70 }, { 76, 142 }, { 157, 134 }, { 125, 125 }, { 183, 137 }, { 22, 163 }, { 0, 138 }, { 29, 48 }, { 19, 159 }, { 68, 95 }, { 180, 97 }, { 185, 17 }, { 172, 15 }, { 81, 89 }, { 156, 79 }, { 171, 82 }, { 182, 56 }, { 114, 137 }, { 35, 143 }, { 53, 114 }, { 146, 106 }, { 69, 98 }, { 10, 152 }, { 154, 61 }, { 76, 18 }, { 194, 106 }, { 184, 59 }, { 175, 83 }, { 187, 136 }, { 68, 84 }, { 74, 107 }, { 132, 117 }, { 141, 61 }, { 111, 140 }, { 27, 80 }, { 25, 58 }, { 157, 42 }, { 177, 22 }, { 2, 96 }, { 27, 131 }, { 191, 111 }, { 34, 106 }, { 192, 40 }, { 174, 67 }, { 120, 64 }, { 81, 51 }, { 10, 75 }, { 80, 11 }, { 128, 21 }, { 55, 50 }, { 1, 155 }, { 53, 154 }, { 149, 77 }, { 83, 134 }, { 79, 26 }, { 48, 152 }, { 32, 63 }, { 176, 105 }, { 127, 147 }, { 17, 13 }, { 188, 111 }, { 0, 64 }, { 2, 28 }, { 198, 97 }, { 15, 139 }, { 59, 78 }, { 1, 9 }, { 5, 82 }, { 174, 96 }, { 109, 45 }, { 32, 15 }, { 54, 151 }, { 187, 137 }, { 19, 148 }, { 188, 18 }, { 163, 147 }, { 192, 58 }, { 156, 139 }, { 55, 110 }, { 129, 158 }, { 147, 2 }, { 169, 62 }, { 89, 117 }, { 198, 150 }, { 130, 132 }, { 6, 98 }, { 22, 110 }, { 122, 163 }, { 62, 162 }, { 57, 37 }, { 100, 119 }, { 160, 107 }, { 129, 136 }, { 128, 113 }, { 35, 59 }, { 100, 150 }, { 85, 54 }, { 77, 105 }, { 141, 86 }, { 27, 19 }, { 37, 59 }, { 40, 56 }, { 188, 28 }, { 135, 160 }, { 105, 43 }, { 187, 14 }, { 3, 142 }, { 89, 50 }, { 34, 93 }, { 170, 78 }, { 29, 49 }, { 146, 53 }, { 113, 30 }, { 97, 64 }, { 130, 130 }, { 70, 45 }, { 65, 114 }, { 67, 127 }, { 98, 58 }, { 193, 25 }, { 105, 122 }, { 65, 54 }, { 17, 156 }, { 75, 82 }, { 97, 106 }, { 47, 25 }, { 187, 1 }, { 6, 72 }, { 65, 40 }, { 3, 125 }, { 14, 102 }, { 196, 131 }, { 7, 107 }, { 199, 3 }, { 100, 6 }, { 19, 151 }, { 63, 152 }, { 4, 101 }, { 184, 89 }, { 10, 17 }, { 142, 37 }, { 59, 143 }, { 98, 132 }, { 102, 159 }, { 49, 43 }, { 193, 102 }, { 193, 140 }, { 110, 32 }, { 65, 55 }, { 7, 138 }, { 67, 55 }, { 131, 162 }, { 98, 154 }, { 139, 142 }, { 199, 68 }, { 27, 151 }, { 174, 88 }, { 117, 1 }, { 146, 13 }, { 41, 7 }, { 173, 83 }, { 92, 123 }, { 179, 85 }, { 119, 41 }, { 189, 46 }, { 191, 34 }, { 120, 110 }, { 199, 154 }, { 151, 64 }, { 115, 93 }, { 85, 16 }, { 4, 48 }, { 195, 149 }, { 172, 111 }, { 2, 98 }, { 64, 112 }, { 166, 83 }, { 189, 155 }, { 104, 97 }, { 120, 89 }, { 22, 54 }, { 22, 136 }, { 136, 134 }, { 77, 121 }, { 58, 21 }, { 69, 93 }, { 25, 83 }, { 191, 50 }, { 28, 64 }, { 184, 110 }, { 129, 17 }, { 193, 118 }, { 184, 132 }, { 79, 160 }, { 104, 71 }, { 18, 79 }, { 180, 20 }, { 4, 148 }, { 138, 89 }, { 176, 135 }, { 16, 159 }, { 92, 116 }, { 11, 162 }, { 188, 60 }, { 5, 23 }, { 92, 64 }, { 162, 130 }, { 26, 128 }, { 48, 107 }, { 174, 90 }, { 192, 163 }, { 127, 54 }, { 188, 43 }, { 160, 100 }, { 171, 111 }, { 101, 101 }, { 198, 33 }, { 31, 74 }, { 26, 135 }, { 157, 139 }, { 18, 108 }, { 14, 132 }, { 56, 45 }, { 167, 6 }, { 124, 71 }, { 181, 115 }, { 44, 102 }, { 138, 91 }, { 39, 63 }, { 44, 94 }, { 22, 47 }, { 98, 97 }, { 7, 97 }, { 52, 139 }, { 72, 149 }, { 134, 39 }, { 21, 124 }, { 24, 156 }, { 126, 28 }, { 197, 31 }, { 63, 68 }, { 118, 98 }, { 181, 41 }, { 163, 142 }, { 138, 68 }, { 133, 32 }, { 198, 85 }, { 187, 22 }, { 2, 81 }, { 190, 125 }, { 17, 7 }, { 19, 2 }, { 174, 117 }, { 61, 9 }, { 188, 121 }, { 77, 77 }, { 158, 112 }, { 175, 31 }, { 116, 24 }, { 98, 139 }, { 6, 154 }, { 19, 85 }, { 176, 51 }, { 110, 106 }, { 195, 42 }, { 147, 114 }, { 123, 3 }, { 9, 46 }, { 162, 104 }, { 42, 154 }, { 64, 129 }, { 122, 125 }, { 13, 107 }, { 20, 134 }, { 85, 25 }, { 195, 139 }, { 119, 127 }, { 27, 89 }, { 74, 84 }, { 90, 130 }, { 109, 144 }, { 113, 108 }, { 51, 102 }, { 100, 36 }, { 97, 109 }, { 85, 66 }, { 168, 74 }, { 71, 36 }, { 33, 26 }, { 69, 38 }, { 83, 9 }, { 104, 39 }, { 103, 31 }, { 0, 126 }, { 48, 33 }, { 76, 14 }, { 122, 135 }, { 135, 149 }, { 54, 35 }, { 34, 150 }, { 23, 104 }, { 56, 114 }, { 132, 92 }, { 49, 32 }, { 18, 44 }, { 182, 17 }, { 79, 3 }, { 124, 24 }, { 28, 0 }, { 6, 43 }, { 190, 92 }, { 68, 44 }, { 97, 148 }, { 165, 151 }, { 54, 109 }, { 120, 32 }, { 73, 127 }, { 191, 62 }, { 91, 104 }, { 6, 108 }, { 30, 131 }, { 173, 67 }, { 134, 132 }, { 3, 51 }, { 21, 99 }, { 121, 0 }, { 156, 7 }, { 196, 112 }, { 124, 22 }, { 140, 99 }, { 60, 64 }, { 149, 43 }, { 168, 101 }, { 160, 38 }, { 57, 110 }, { 96, 19 }, { 162, 141 }, { 129, 114 }, { 21, 131 }, { 195, 4 }, { 170, 112 }, { 88, 58 }, { 98, 6 }, { 61, 92 }, { 0, 30 }, { 158, 147 }, { 140, 161 }, { 123, 15 }, { 37, 21 }, { 32, 116 }, { 84, 59 }, { 5, 18 }, { 180, 119 }, { 151, 68 }, { 145, 142 }, { 180, 135 }, { 121, 4 }, { 84, 109 }, { 140, 136 }, { 58, 49 }, { 78, 64 }, { 21, 54 }, { 139, 74 } };
	final static private int[] samples =
		new int[]{ 69, 205, 175, 222, 207, 145, 220, 85, 0, 134, 0, 13, 185, 169, 170, 134, 212, 220, 153, 0, 255, 145, 208, 255, 160, 242, 217, 248, 28, 255, 113, 247, 255, 181, 136, 221, 255, 167, 255, 157, 166, 226, 113, 204, 199, 162, 248, 112, 0, 241, 222, 221, 222, 255, 205, 127, 213, 0, 179, 119, 66, 93, 253, 255, 167, 194, 175, 155, 154, 177, 170, 231, 134, 162, 170, 0, 174, 255, 168, 0, 253, 42, 0, 208, 209, 108, 255, 168, 250, 175, 0, 167, 255, 229, 179, 255, 215, 231, 208, 182, 175, 255, 128, 0, 189, 204, 13, 192, 187, 192, 13, 109, 255, 193, 155, 210, 231, 167, 182, 175, 148, 149, 170, 184, 34, 167, 98, 252, 0, 180, 147, 0, 203, 185, 180, 236, 215, 169, 120, 145, 195, 219, 210, 173, 56, 128, 230, 155, 172, 0, 254, 75, 131, 255, 186, 225, 0, 215, 217, 197, 255, 197, 99, 249, 241, 147, 0, 255, 245, 224, 0, 187, 235, 59, 221, 210, 195, 178, 165, 135, 195, 197, 50, 252, 229, 195, 220, 142, 149, 221, 165, 148, 209, 223, 206, 251, 147, 212, 199, 113, 202, 178, 143, 210, 178, 46, 218, 237, 254, 92, 166, 151, 13, 154, 133, 219, 175, 228, 0, 0, 149, 50, 255, 208, 167, 185, 222, 219, 102, 99, 150, 182, 187, 136, 0, 133, 0, 203, 0, 144, 159, 172, 166, 231, 255, 115, 178, 179, 226, 215, 215, 0, 13, 202, 233, 0, 169, 208, 0, 255, 88, 251, 255, 179, 199, 180, 218, 146, 141, 142, 142, 120, 193, 231, 229, 255, 170, 255, 223, 173, 182, 192, 189, 69, 214, 178, 248, 133, 177, 178, 209, 226, 215, 159, 0, 178, 250, 171, 154, 253, 184, 214, 130, 128, 231, 223, 0, 206, 169, 138, 253, 230, 215, 176, 131, 199, 189, 197, 211, 108, 138, 224, 255, 118, 255, 175, 243, 140, 0, 213, 220, 145, 223, 131, 147, 188, 212, 178, 140, 218, 139, 34, 222, 131, 180, 219, 162, 189, 34, 127, 255, 254, 13, 108, 145, 177, 157, 233, 168, 230, 255, 96, 210, 201, 175, 202, 217, 13, 207, 0, 175, 219, 0, 0, 222, 196, 205, 120, 99, 196, 255, 169, 223, 0, 0, 185, 253, 255, 130, 205, 243, 199, 175, 255, 22, 157, 217, 202, 117, 228, 34, 114, 190, 224, 208, 221, 122, 231, 255, 220, 213, 247, 216, 190, 192, 231, 64, 137, 222, 156, 140, 212, 13, 198, 167, 79, 216, 200, 119, 191, 134, 171, 223, 146, 155, 223, 202, 149, 253, 71, 0, 255, 255, 167, 254, 227, 0, 255, 155, 228, 128, 0, 230, 42, 255, 28, 175, 193, 194, 230, 255, 202, 173, 219, 224, 213, 0, 46, 227, 206, 202, 222, 0, 220, 187, 212, 196, 0, 223, 179, 0, 197, 138, 220, 214, 255, 192, 166, 224, 255, 0, 204, 135, 202, 221, 223, 170, 0, 185, 166, 117, 169, 13, 77, 189, 215, 159, 202, 178, 226, 220, 179, 180, 161, 255, 206, 206, 114, 218, 129, 186, 137, 182, 138, 250, 56, 0, 182, 192, 167, 90, 0, 177, 248, 205, 218, 0, 221, 0, 153, 142, 185, 238, 196, 255, 241, 223, 161, 194, 149, 120, 252, 71, 0, 71, 248, 167, 181, 115, 211, 42, 166, 0, 165, 147, 95, 203, 209, 193, 124, 201, 118, 220, 0, 228, 153, 247, 255, 170, 222, 105, 177, 0, 179, 197, 148, 0, 201, 59, 167, 217, 255, 136, 192, 140, 168, 255, 0, 150, 202, 178, 244, 226, 93, 66, 223, 197, 136, 160, 185, 173, 196, 0, 85, 174, 252, 204, 175, 208, 182, 170, 0, 121, 185, 218, 198, 240, 148, 250, 0, 198, 218, 177, 28, 150, 161, 205, 169, 173, 238, 148, 252, 255, 254, 179, 255, 219, 249, 157, 219, 199, 0, 176, 230, 77, 156, 185, 155, 218, 22, 160, 232, 255, 229, 178, 0, 233, 190, 95, 160, 145, 152, 185, 0, 224, 143, 255, 0, 162, 215, 213, 148, 0, 0, 223, 181, 95, 0, 151, 162, 185, 221, 171, 201, 242, 142, 121, 237, 0, 0, 254, 223, 218, 0, 0, 242, 147, 195, 127, 0, 255, 150, 140, 230, 255, 203, 95, 99, 250, 184, 255, 129, 255, 247, 69, 158, 215, 197, 227, 136, 146, 181, 216, 90, 205, 187, 184, 170, 144, 170, 139, 183, 150, 255, 98, 164, 215, 189, 141, 190, 202, 118, 108, 145, 183, 226, 134, 203, 96, 246, 229, 90, 208, 180, 0, 167, 255, 214, 255, 221, 128, 219, 255, 71, 255, 248, 0, 64, 151, 179, 171, 117, 230, 255, 199, 98, 255, 81, 118, 193, 93, 255, 255, 241, 210, 137, 209, 176, 182, 175, 0, 232, 167, 112, 255, 170, 224, 228, 0, 255, 253, 249, 171, 169, 0, 211, 181, 181, 255, 135, 173, 229, 223, 109, 235, 194, 255, 185, 255, 224, 130, 224, 13, 218, 0, 194, 254, 132, 159, 0, 230, 0, 188, 255, 251, 183, 255, 0, 143, 234, 192, 255, 135, 155, 195, 255, 237, 246, 255, 117, 237, 176, 240, 172, 185, 200, 196, 176, 194, 203, 129, 180, 0, 255, 255, 185, 135, 153, 195, 232, 0, 188, 133, 173, 148, 0, 64, 134, 158, 251, 220, 244, 224, 0, 227, 209, 121, 0, 0, 231, 114, 201, 142, 193, 0, 255, 159, 190, 148, 221, 255, 232, 255, 164, 93, 231, 163, 209, 79, 171, 206, 146, 150, 255, 210, 187, 204, 222, 182, 192, 255, 221, 230, 102, 101, 222, 173, 255, 192, 156, 212, 96, 221, 218, 188, 187, 0, 0, 117, 222, 13, 204, 188, 71, 228, 56, 216, 255, 216, 122, 0, 180, 202, 237, 254, 192, 132, 161, 120, 228, 0, 202, 209, 124, 151, 255, 131, 244, 151, 218, 218, 0, 13, 161, 202, 194, 181, 164, 0, 251, 169, 210, 251, 196, 200, 124, 73, 114, 171, 187 };
	
	private KNearestNeighborSearchBehavior(){}
	
	final static public void main( final String[] args )
	{
		final RealPointSampleList< UnsignedByteType > list = new RealPointSampleList< UnsignedByteType >( 2 );
		for ( int i = 0; i < samples.length; ++i )
			list.add( new RealPoint( coordinates[ i ] ), new UnsignedByteType( samples[ i ] ) );
		
		final ImagePlusImgFactory< UnsignedByteType > factory = new ImagePlusImgFactory< UnsignedByteType >();
		
		new ImageJ();	
		
		/* nearest neighbor */
		IJ.log( "Nearest neighbor ..." );
		final ImagePlusImg< UnsignedByteType, ? > img1 = factory.create( size, new UnsignedByteType() );
		final Cursor< UnsignedByteType > c1 = img1.localizingCursor();
		final NearestNeighborSearchOnIterableRealInterval< UnsignedByteType > search1 = new NearestNeighborSearchOnIterableRealInterval< UnsignedByteType >( list );
		while ( c1.hasNext() )
		{
			c1.fwd();
			search1.search( c1 );
			c1.get().set( search1.getSampler().get() );
		}
		
		try
		{
			img1.getImagePlus().show();
			IJ.log( "Done." );
		}
		catch ( ImgLibException e )
		{
			IJ.log( "Didn't work out." );
			e.printStackTrace();
		}
		
		/* nearest neighbor through k-nearest-neighbor search */
		IJ.log( "Nearest neighbor through k-nearest-neighbor search ..."  );
		final ImagePlusImg< UnsignedByteType, ? > img2 = factory.create( size, new UnsignedByteType() );
		final Cursor< UnsignedByteType > c2 = img2.localizingCursor();
		final KNearestNeighborSearchOnIterableRealInterval< UnsignedByteType > search2 = new KNearestNeighborSearchOnIterableRealInterval< UnsignedByteType >( list, 1 );
		while ( c2.hasNext() )
		{
			c2.fwd();
			search2.search( c2 );
			c2.get().set( search2.getSampler( 0 ).get() );
		}
		
		try
		{
			img2.getImagePlus().show();
			IJ.log( "Done." );
		}
		catch ( ImgLibException e )
		{
			IJ.log( "Didn't work out." );
			e.printStackTrace();
		}
		
		/* weighted by distance */
		IJ.log( "Weighted by distance ..." );
		final ImageStack distanceStack = new ImageStack( ( int )size[ 0 ], ( int )size[ 1 ] );
		
		int np = 1;
		for ( int k = 5; k <= 30; k += 5 )
		{
			np = 0;
			for ( double p = 0; p <= 2; p += 0.5, ++np )
			{
				IJ.log( "  k=" + k + " p=" + String.format( "%.2f", p ) );
				final ImagePlusImg< UnsignedByteType, ? > img3 = factory.create( size, new UnsignedByteType() );
				final Cursor< UnsignedByteType > c3 = img3.localizingCursor();
				final KNearestNeighborSearchOnIterableRealInterval< UnsignedByteType > search3 = new KNearestNeighborSearchOnIterableRealInterval< UnsignedByteType >( list, k );
				while ( c3.hasNext() )
				{
					c3.fwd();
					search3.search( c3 );
					double s = 0;
					double v = 0;
					for ( int i = 0; i < k; ++i )
					{
						final double d = search3.getSquareDistance( i );
						if ( d > 0.001 )
						{
							final double w = 1.0 / Math.pow(  d, p );
							v += w * search3.getSampler( i ).get().getRealDouble();
							s += w;
						}
						else
						{
							s = 1.0;
							v = search3.getSampler( i ).get().getRealDouble();
						}
					}
					v /= s;
					
					c3.get().set( Math.max(  0, Math.min( 255, ( int )Math.round( v ) ) ) );
				}
				try
				{
					distanceStack.addSlice( "k=" + k + " p=" + String.format( "%.2f", p ), img3.getImagePlus().getProcessor() );
				}
				catch ( final ImgLibException e )
				{
					IJ.log( "Didn't work out." );
					e.printStackTrace();
				}
			}
		}
		
		final ImagePlus impDistance = new ImagePlus( "weighted by distance", distanceStack );
		impDistance.setOpenAsHyperStack( true );
		impDistance.setDimensions( 1, np, distanceStack.getSize() / np );
		impDistance.show();
		IJ.log( "Done." );
	}
}
