package retailer;

import java.lang.Math; 

public class Retailer {
	// init
    // e_a: Độ co giãn quảng cáo của nhà bán lẻ i
    // K: Quy mô thị trường của nhà bán lẻ i
    // phi: Chi phí vận chuyển cho nhà bán lẻ i
    // uc: Nhà sản xuất quản lý hàng tồn kho của nhà bán lẻ i và lấy chi phí uci với mỗi sản phẩm
    // cp: Giá mua 1 sản phẩm từ nhà bán lẻ i
    // H_b: Chi phí giữ hàng phía nhà bán lẻ i
    // L_b: Backorder cost của nhà bán lẻ i
    // S_b: Chi phí cố định bổ sung cho nhà bán lẻ i
    // p: Giá bán sản phẩm của nhà bán lẻ i
    // a: chi phí quảng cáo của nhà bán lẻ i
    // theta: Lợi nhuận mở rộng
    // b: tỉ lệ thời gian tồn đọng hàng trong 1 chu kỳ của nhà bán lẻ i
	
	public double e_a;
	public double K;
	public double phi;
	public double uc;
	public double cp;
	public double H_b;
	public double L_b;
	public double S_b;
	public double p;
	public double a;
	public double theta;
	public double b;

	public Retailer(double e_a, double K, double phi, double uc, double cp, double H_b, double L_b, double S_b, double p, double a, double theta, double b) {
		this.e_a = e_a;
		this.K = K;
		this.phi = phi;
		this.uc = uc;
		this.cp = cp;
		this.H_b = H_b;
		this.L_b = L_b;
		this.S_b = S_b;
		this.p = p;
		this.a = a;
	}
	
	public static void main(String args[]) {
        System.out.println("Retailer");
    }
	
    // calculator_demand: Tính nhu cầu sản phẩm trên mỗi đơn vị thời gian của nhà bán lẻ
	public double calculator_demand(double A, double e_A) {
        double D = this.K * Math.pow(this.a, this.e_a) * Math.pow(A, e_A);
        return D;
	}
	
	// calculator_retailer_profit: Tính lợi nhuận của nhà bán lẻ
	// Tổng thu - Tổng chi
	// Nhu cầu * giá bán p - nhu cầu * (giá mua cp + giá trả uc + giá vận chuyển phi) - chi phí quảng cáo a
	public double calculator_retailer_profit(double A, double e_A) {
		double NP_bi = this.calculator_demand(A, e_A) * (this.p - this.cp - this.uc - this.phi) - this.a;
        return NP_bi;
	}
	
	public void calculator_b() {
		this.b = this.H_b / (this.H_b + this.L_b);
	}
}
