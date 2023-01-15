package デフォルトパッケージ;
//勤怠の給与計算システムです.//
//内容//
//・時給は1000円とする。//
//・給与は1分単位で払う。//
//・小数点以下で払う.//
//・労働時間が６時間以上8時間以下の場合は45分休憩する。//
//・労働時間が8時間以上の場合は60分休憩する。//
//・労働時間から休憩時間を引いた時間が8時間以上の場合、超えた時間は1.5倍の残業代とする。//

import java.sql.Time;

public class 勤怠の給与計算システム {
	public static void main(String[] args) {
		
		final int    HOURLY_SALARY            = 900                          ; 
		final int    MINUTES_SALARY           = HOURLY_SALARY / 60           ; 
		final int    CONV_HOUR_TO_MILLISEC    = 1000 * 60 * 60               ; 
		final int    CONV_MINUTE_TO_MILLISEC  = 1000 * 60                    ; 
		final int    CONV_HOUR_TO_MINUTE      = 60                           ; 
		final long   WORK_TIME_TYPE1_END      = 6  * CONV_HOUR_TO_MILLISEC   ; 
		final long   WORK_TIME_TYPE2_START    = 6  * CONV_HOUR_TO_MILLISEC   ; 
		final long   WORK_TIME_TYPE2_END      = 8  * CONV_HOUR_TO_MILLISEC   ; 
		final long   WORK_TIME_TYPE3_START    = 8  * CONV_HOUR_TO_MILLISEC   ; 
		final long   REST_TIME_TYPE1          = 45 * CONV_MINUTE_TO_MILLISEC ; 
		final long   REST_TIME_TYPE2          = 60 * CONV_MINUTE_TO_MILLISEC ; 
		final double OVERTIME_SALARY_RATE     = 1.5                         ; 
		final int    ACTUAL_WORK_TIME_OVERTIME_OCCUR_MIN = 8  * CONV_HOUR_TO_MINUTE ; 
		
		Time startTime            = Time.valueOf(args[0])                      ; 
		Time finishTime           = Time.valueOf(args[1])                      ; 
		long workTime             = finishTime.getTime() - startTime.getTime() ; 
		int  actualWorkTimeMin    = 0                                          ; 
		int  partTimeFee          = 0                                          ; 
		
		if (workTime <= WORK_TIME_TYPE1_END) {
			
			actualWorkTimeMin = (int)( workTime / CONV_MINUTE_TO_MILLISEC ) ;
			
		} else if (workTime > WORK_TIME_TYPE2_START && workTime <= WORK_TIME_TYPE2_END) {
			
			actualWorkTimeMin = (int)( (  workTime - REST_TIME_TYPE1 ) / CONV_MINUTE_TO_MILLISEC ) ;
			
		} else if (workTime > WORK_TIME_TYPE3_START){
			
			actualWorkTimeMin = (int)( ( workTime - REST_TIME_TYPE2 ) / CONV_MINUTE_TO_MILLISEC ) ;
			
		}
		
		if( actualWorkTimeMin > ACTUAL_WORK_TIME_OVERTIME_OCCUR_MIN ){
	
			partTimeFee = ( MINUTES_SALARY * ACTUAL_WORK_TIME_OVERTIME_OCCUR_MIN )
			              + (int)( MINUTES_SALARY * OVERTIME_SALARY_RATE * ( actualWorkTimeMin - ACTUAL_WORK_TIME_OVERTIME_OCCUR_MIN ) ) ;
			
		}else{
			
			partTimeFee = MINUTES_SALARY * actualWorkTimeMin ;
		}
		
		System.out.println("ごくろうさまでした。本日の給与は" + partTimeFee + "円です");
	}
}
