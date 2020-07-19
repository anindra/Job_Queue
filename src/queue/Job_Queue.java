package queue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Job_Queue
{

    ArrayList<String> queue = new ArrayList<String>();
    ArrayList<String> job = new ArrayList<String>();

    public Job_Queue(ArrayList<String> job)
    {
        this.job = job;
    }

    /**
     * triggered when the system is started initially, which then triggers a scheduler method
     * This method also picks up all the job that has been in sleep during the time when system was down
     */
    public void startQueue()
    {
        //add all the jobs from the list
        for(int i=0; i< job.size();i++)
        {
            queue.add(job.get(i));
        }

        scheduler();
    }

    public void scheduler()
    {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool ( 1 );

        Runnable r = new Runnable()
        {
            @Override
            public void run ()
            {
                try
                {
                    System.out.println ( "Now: " + Instant.now () );
                    if ( Boolean.FALSE )
                    {
                        executor.shutdown ();  // 'shutdown' politely asks ScheduledExecutorService to terminate after previously submitted tasks are executed.
                    }

                } catch ( Exception e ) {
                    System.out.println ( "Oops, uncaught Exception surfaced at Runnable in ScheduledExecutorService." );
                }
            }
        };

        try {
            executor.scheduleAtFixedRate ( r , 0L , 5L , TimeUnit.SECONDS ); // ( runnable , initialDelay , period , TimeUnit )
            Thread.sleep ( TimeUnit.MINUTES.toMillis ( 1L ) ); // Let things run a minute to witness the background thread working.
        }
        catch ( InterruptedException ex )
        {
        }
    }


    public static void main(String args[])
    {
        System.out.println("Starting");
        ArrayList<String> job = new ArrayList<>();
        job.add("1");
        job.add("2");
        job.add("3");
        job.add("4");

        Job_Queue obj = new Job_Queue(job);
        obj.startQueue();


    }
}
