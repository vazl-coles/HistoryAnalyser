Build with:
d:\Java Projects\HistoryAnalyser>mvn clean install

also 

mvn package


Run with:
d:\Java Projects\HistoryAnalyser>java -cp target/HistoryAnalyser-1.0-jar-with-dependencies.jar com.vadim.HistoryAnalyser

To push to github from command line:
D:\Java Projects\HistoryAnalyser [master ↑ +5 ~5 -3 !]> git push origin master
Counting objects: 10, done.
Delta compression using up to 2 threads.
Compressing objects: 100% (7/7), done.
Writing objects: 100% (10/10), 2.12 KiB | 0 bytes/s, done.
Total 10 (delta 2), reused 0 (delta 0)
remote: Resolving deltas: 100% (2/2), completed with 2 local objects.
To https://github.com/vazl/HistoryAnalyser.git
   72b0842..0620463  master -> master
D:\Java Projects\HistoryAnalyser [master ≡ +5 ~5 -3 !]>

Input files:

spy history.xltm
and
vix history.xltm

These files were created to automate the process of downloading data. They are used to save information in csv format.

Another class that can be activated is StatsAnalyser which will calculate probabilities for current day only:

java -cp target/HistoryAnalyser-1.0-jar-with-dependencies.jar com.vadim.StatsAnalyser

Run DecisionMaker as follows:
java -cp target/HistoryAnalyser-1.0-jar-with-dependencies.jar com.vadim.DecisionTaker
