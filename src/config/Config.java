package config;

public class Config {
	public double e_A = 0.19; // Độ co giãn quảng cáo của nhà sản xuất
	public double P = 15000; // Công suất nhà sản xuất
	public double H_p = 4; // Chi phí giữ hàng phía nhà sản xuất
	public double S_p = 200; // Chi phí thiết lập sản xuất của nhà sản xuất ($/setup)
	public double cm = 20; // Chi phí sản xuất 1 sản phẩm ($/unit)


	public double[] cr = {20, 22}; // Chi phí mua 1 đơn vị nguyên liệu thô ($/unit)
	public double[] H_r = {2, 3}; // Chi phí giữ nguyên liệu thô j ($/unit/time)
	public double M[] = {1.1, 1.5}; // Số nguyên liệu thô để sản xuất 1 sản phầm j
	public double S_r[] = {500, 525}; // Chi phí mua nguyên liệu thô cố định ($/order)


	public double e_a[] = {0.19, 0.2, 0.21, 0.22, 0.23}; // Độ co giãn quảng cáo của nhà bán lẻ i
	public double K[] = {27, 30, 31, 33, 37}; // Quy mô thị trường của nhà bán lẻ i
	public double phi[] = {7, 10, 13, 16, 19}; // Chi phí vận chuyển cho nhà bán lẻ i
	public double uc[] = {10, 13, 18, 21, 25}; // Nhà sản xuất quản lý hàng tồn kho của nhà bán lẻ i và lấy chi phí uci với mỗi sản phẩm
	public double cp[] = {200, 202.4, 204.8, 207.2, 209.6}; // Giá mua 1 sản phẩm từ nhà bán lẻ i
	public double p[] = {300, 303.2, 305.1, 307.5, 310}; // Giá bán sản phẩm của nhà bán lẻ i
	public double S_b[] = {100, 102, 105, 107, 110}; // Chi phí cố định bổ sung cho nhà bán lẻ i
	public double H_b[] = {8, 12, 16, 20, 24}; // Chi phí giữ hàng phía nhà bán lẻ i
	public double L_b[] = {500, 505, 510, 520, 525}; // Backorder cost của nhà bán lẻ i
}
