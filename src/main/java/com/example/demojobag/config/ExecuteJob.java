package com.example.demojobag.config;

import org.springframework.stereotype.Component;

@Component
public class ExecuteJob {



    public void execute(MeuJob job){
        new Thread(() -> {
            try {
                System.out.println( "Rodando o job: "+ job.getId() );
                //onEvent( job );
            } catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    //protected abstract void onEvent(MeuJob job) throws Exception;

}
