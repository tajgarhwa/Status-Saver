package android.statussaver.com.statussaver.service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

//import com.firebase.jobdispatcher.JobParameters;
//import com.firebase.jobdispatcher.JobService;

public class StatusListenerService {

        //extends JobService {

//    StatusTask statusBackgroundTask;
//    private static  int i=0;
//    @Override
//    public boolean onStartJob(final JobParameters job) {
//
//        statusBackgroundTask = new StatusTask(){
//            @Override
//            protected void onPostExecute(String s) {
//                Toast.makeText(getApplicationContext(),"Message from Background task:"+s,Toast.LENGTH_SHORT).show();
//                Log.e("task", String.valueOf(i));
//                jobFinished(job,true);
//                i++;
//            }
//        };
//
//        statusBackgroundTask.execute();
//        return true;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters job) {
//        return true;
//    }
//
//    public static class StatusTask extends AsyncTask<Void,Void,String>{
//
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            return "BackGround job";
//        }
//    }
}