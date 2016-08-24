package com.github.gregwhitaker.rxbackpressure;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ExampleRunner {

    public static void main(String... args) throws Exception {
        final CountDownLatch latch = new CountDownLatch(Integer.MAX_VALUE);

        Observable.interval(1_000, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer(10)
                .map(i -> new ValueHolder(i, "Message" + i))
                .subscribeOn(Schedulers.computation())
                .subscribe(new Subscriber<ValueHolder>() {
                    @Override
                    public void onCompleted() {
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        latch.countDown();
                    }

                    @Override
                    public void onNext(ValueHolder valueHolder) {
                        if (valueHolder.getCount() != 0 && valueHolder.getCount() % 10 == 0) {
                            try {
                                System.out.println("Backpressure!");
                                Thread.sleep(5_000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.println(valueHolder.getMessage());

                        request(1);
                        latch.countDown();
                    }
                });

        latch.await();
        System.exit(0);
    }

    private static class ValueHolder {
        private final Long count;
        private final String message;

        public ValueHolder(Long count, String message) {
            this.count = count;
            this.message = message;
        }

        public Long getCount() {
            return count;
        }

        public String getMessage() {
            return message;
        }
    }
}
