rxjava-backpressure-example
===

This example shows how backpressure can be used to throttle incoming events from an Observable.

In the example an Observable is created that emits a message once per second.  If the message number of the emitted 
message is evenly divisible by ten the subscriber will sleep for five seconds, during which time backpressure will cause
the emitting observable to buffer messages.

##Running the Example
The example can be run using the following gradle command:

```
$ ./gradlew run
```

##License
Copyright 2016 Greg Whitaker

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.