package vmi;

import config.Config;
import ga.GA;
import manufacturer.Manufacturer;

public class VMI {
	
	public static void main(String args[]) {
		Config config = new Config();
		GA ga = new GA(1000);
		ga.create();
		for (int i = 0; i < 10; i++) {
		    ga.evaluation();
		    ga.selection();
		    ga.selection();
		    ga.crossover();
		    ga.mutation();
		    ga.show();
		}
		Manufacturer manufacture = ga.manufacturer_list.get(ga.best_id);
		double e_M = manufacture.e_A;
		double A = manufacture.A;
		double sum_e_bi = 0;
		for (int i = 0; i < config.e_a.length; i ++) {
		    sum_e_bi += manufacture.retailer_list.get(i).e_a;
		}
		System.out.println(ga.profit_list.get(ga.best_id));
		for (int i = 0; i < config.e_a.length; i ++) {
			System.out.println(i);
			double pi = manufacture.retailer_list.get(i).p;
			double cpi = manufacture.retailer_list.get(i).cp;
			double uci = manufacture.retailer_list.get(i).uc;
			double e_bi = manufacture.retailer_list.get(i).e_a;
			double bi = manufacture.retailer_list.get(i).b;
			double delta_NP_bi = (e_bi/(e_M + sum_e_bi))*ga.profit_list.get(ga.best_id);
			double NP_bi = manufacture.retailer_list.get(i).calculator_retailer_profit(A, e_M);
		    double a_i = manufacture.retailer_list.get(i).a;
		    double deman_i = manufacture.retailer_list.get(i).calculator_demand(A, e_M);
		    double theta = pi - cpi - uci - 1.0 * (delta_NP_bi + NP_bi + a_i)/deman_i;
		    System.out.println(a_i);
		    System.out.println(bi);
		    System.out.println(theta);
		}
    }
}
