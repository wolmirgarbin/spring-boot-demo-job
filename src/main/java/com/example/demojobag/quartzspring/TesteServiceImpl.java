package com.example.demojobag.quartzspring;

import org.springframework.stereotype.Service;

@Service
public class TesteServiceImpl implements TesteService {

    public void executeSampleJob() {
        System.out.println( "Chamou o service" );
    }

}
