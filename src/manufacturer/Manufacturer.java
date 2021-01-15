package manufacturer;

import retailer.Retailer;
import java.util.ArrayList;
import java.lang.Math; 

public class Manufacturer {
	// init
	// cm: Chi phí sản xuất 1 sản phẩm ($/unit)
	// cr: Chi phí mua 1 đơn vị nguyên liệu thô ($/unit)
	// H_p: Chi phí giữ hàng phía nhà sản xuất ($/unit/time)
	// H_r: Chi phí giữ nguyên liệu thô ($/unit/time)
	// M: Số nguyên liệu thô để sản xuất 1 sản phầm
	// P: Công suất nhà sản xuất
	// S_p: Chi phí thiết lập sản xuất của nhà sản xuất ($/setup)
	// S_r: Chi phí mua nguyên liệu thô cố định ($/order)
	// A: Chi phí quảng cáo của nhà sản xuất
	// e_A: Độ co giãn quảng cáo của nhà sản xuất
	// C: Chu kỳ bổ sung sản phẩm cho nhà bán lẻ
	// n: Chu kỳ mua nguyên liệu thô là C*n
	// x: nếu công suất P > tổng nhu cầu thì x = 1 còn P = tổng nhu cầu thì x = 0
	
	double cm;
	double cr;
	double H_p;
	double H_r;
	double M;
	double P;
	double S_p;
	double S_r;
	ArrayList<Retailer> retailer_list;
	double A;
	double e_A;
	double C;
	double n;
	double x;

	public static void main(String args[]) {
        System.out.println("Manufacturer");
    }
	
	// Thêm các nhà bán lẻ vào retailer_list
	public void calculator_demand(Retailer retailer) {
		retailer_list.add(retailer);
	}
	
	// Lợi nhuận NP_M = Tổng thu TR_M - Tổng chi TC_M
	public double calculator_manufacturer_profit(Retailer retailer) {
		double NP_M = this.get_TR_M() - this.get_TC_M();
		return NP_M;
	}
	
   // TR_M có 3 thành phần
   // 1: Giá bán buôn sản phẩm
   // 2: Chi phí coi hàng được trả bởi nhà bán lẻ
   // 3: Lợi nhuận mở rộng
    public double get_TR_M() {
        double TR_M = 0;
        for (int i = 0; i < retailer_list.size(); i++) {
            double demand_i = this.retailer_list.get(i).calculator_demand(this.A, this.e_A);
            TR_M += demand_i * this.retailer_list.get(i).cp;
            TR_M += demand_i * this.retailer_list.get(i).uc;
            TR_M += demand_i * this.retailer_list.get(i).theta;
        }
        return TR_M;
	}
	
   // TC_M gồm:
   // Tổng chi phí trực tiếp TDC_M
   // Tổng chi phí gián tiếp TIDC_M
	public double get_TC_M() {
		double TC_M = this.get_TDC_M() + this.get_TIDC_M();
        return TC_M;
	}
	
    // TDC_M gồm :
    // Chi phí sản xuất
    // Chi phí vận chuyển 
    // Chi phí mua nguyên liệu
	public double get_TDC_M() {
        double TDC_M = 0;
        for (int i = 0; i < retailer_list.size(); i++) {
        	double demand_i = this.retailer_list.get(i).calculator_demand(this.A, this.e_A);
            TDC_M += demand_i * this.cm;
            TDC_M += demand_i * this.retailer_list.get(i).phi;
            TDC_M += demand_i * this.M * this.cr;
        }
        return TDC_M;
	}
	
    // TIDC_M gồm 3 chi phí con CC
    // Tổng chi phí nhà sản xuất bồi thường cho nhà bán lẻ
    // Tổng chi phí tồn kho TIC
    // Tổng chi phí quảng cáo A
	public double get_TIDC_M() {
		double TIDC_M = this.get_CC() + this.get_TIC() + this.A;
        return TIDC_M;
	}

    // CC gồm:
    // Chi phí ordering, holding, backordering
	public double get_CC() {
		double CC = 0;
		for (int i = 0; i < retailer_list.size(); i++) {
            double demand_i = this.retailer_list.get(i).calculator_demand(this.A, this.e_A);
            CC += this.retailer_list.get(i).S_b / this.C;
            CC += demand_i * Math.pow(1-this.retailer_list.get(i).b,2) * C * this.retailer_list.get(i).H_b / 2;
            CC += demand_i * Math.pow(this.retailer_list.get(i).b,2) * C * this.retailer_list.get(i).L_b / 2;
		}
        return CC;
	}
	
    // TIC gồm
    // TIC_p : Chi phí giữ sản phẩm
    // TIC_r : Chi phí giữ nguyên liệu thô
	public double get_TIC() {
		double TIC = this.get_TIC_p() + this.get_TIC_r();
        return TIC;
	}
	
    // TIC_p goomf:
    // Chi phí thiết lập sản xuất
    // Chi phí giữ hàng phía nhà bán lẻ
	public double get_TIC_p() {
		double TIC_p = this.x * this.S_p / this.C;
		for (int i = 0; i < retailer_list.size(); i++) {
            double demand_i = this.retailer_list.get(i).calculator_demand(this.A, this.e_A);
            TIC_p += this.H_p * this.C / (2*this.P) * Math.pow(demand_i,2);
		}
        return TIC_p;
	}
	
    // TIC_r gồm :
    // Chi phí đặt nguyên liệu thô cố định
    // Chi phí giữ nguyên liệu thô
	public double get_TIC_r() {
        double TIC_r = this.S_r / (n*C) + this.get_HIC();
        return TIC_r;
	}
	
    // get_HIC gồm :
    // Chi phí giữ nguyên liệu trong quá trình sản xuất HIC_d
    // Chi phí giữ nguyên liệu trong kho HIC_r
	public double get_HIC() {
		double HIC = this.get_HIC_d() + this.get_HIC_r();
        return HIC;
	}
	
    public double get_HIC_d() {
        double X = 0;
        for (int i = 0; i < retailer_list.size(); i++) {
            double demand_i = this.retailer_list.get(i).calculator_demand(this.A, this.e_A);
            X += demand_i * this.C;
        }
        double HIC_d = this.n * this.M * Math.pow(X,2) / (2*this.P);
        return HIC_d;
    }
    
    public double get_HIC_r() {
        double X = 0;
        for (int i = 0; i < retailer_list.size(); i++) {
            double demand_i = this.retailer_list.get(i).calculator_demand(this.A, this.e_A);
            X += demand_i;
        }
        double HIC_r = this.n * (this.n - 1) * this.M * this.H_r * X * Math.pow(this.C,2) / 2;
        return HIC_r;
    }
}
