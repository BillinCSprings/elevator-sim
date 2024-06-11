# elevator-sim
A simple elevator simulator

# Directions
## Clone Repo
git clone https://github.com/BillinCSprings/elevator-sim.git
## Compile Code
cd ./elevator-sim <br>
javac -d bins ./src/com/bluestaq/*.java 
## --or--
Import project directory as project in IDE

# Assumptions
<ol>
<li>There shall be a single building, and it shall contain one elevator </li>
<li> The elevator shall travel at a constant speed </li>
<li> The number of floors as well as the elevator's speed shall be configurable values
</ol>

# Future Enhancements
<ol>
<li>The building should contain more than one elevator </li>
<li> The elevator's speed should be adjusted during the travel to low down  it approaches the destination floor; then speed up when it departs</li>
<li> The number of floors as well as the elevator's speed should be configured via .properties file
</ol>


