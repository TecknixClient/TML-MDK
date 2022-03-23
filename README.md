# Tecknix Mod Loader API

**THIS API IS NOT COMPLETE AND WILL NOT YET ALLOW YOU TO MOD THE CLIENT**

[![Discord](https://img.shields.io/badge/chat%20on-discord-7289DA)](https://discord.gg/qn7BsjHHbN)

# What is this?

This is our modding MDK for Tecknix Client. This implementation includes an example mod with mixins pre setup!

# License:

This repository is licenced under the GNU General Public License. Any additions or contributions must include the
license header.

## Setting up:

- Download the zip file from github and open it in your IDE of choice.
- You will notice the API files are missing, download the latest release of the api and put it in a folder called "lib" under the root directory.

- Open the gradle project in Intellij IDEA or Eclipse.
- Run "setupDecompWorkspace" then "setupCiWorkspace".
  ![Image](https://imgur.com/pbLxpi5.png)

<br/>- Run "genIntellijRuns".
![Image](https://imgur.com/gsBTgGv.png)
<br/>- Open the run configuration's settings and change the classpath.
![Image](https://imgur.com/DACiMTi.png)
**And you are done!**

## Contributing

Added an event? Amazing! Be sure to create a pull request with your changes on this repository. Remember to keep
our [Code Style](#code-style) in mind!

## Using the event bus:

### Posting an event:

```java
EventBus.post(new YourEvent());
```

### Listening for an event:

```java
@TMSubscription
public void onChat(TMChatEvent event){
    System.err.println("CHAT: " + event.getFormattedText());
}
```

### Registering an object for listening:

```java
EventBus.register(new YourObject());
```

## Using Transformers:

### Create transformer:

```java
package com.tecknix.modding.examplemod.transformer;

import com.tecknix.modding.api.transform.IModTransformer;

/**
 * Example transformer...
 * This transformer can be used to manipulate classes with raw asm or alternative frameworks!
 *
 * @author Tecknix Software
 */
public class ExampleTransformer implements IModTransformer {

    @Override
    public byte[] transform(String s, byte[] bytes) {
        //Run transformations and return byte array
        return yourByteArray;
    }
}

```

### Registering transformers:

Call this#registerModTransformers in the onEnable method of your main class.

```java
this.registerModTransformers(new Transformer1(), new Transformer2(), new Transformer3());
```

## Code Style:

### Classes:

Classes should have a header containing a short description of the class' purpose.

```java
    /**
 * TMTickEvent
 *
 * This event is called in the {@link net.minecraft.client.Minecraft} class.
 * It can be used for calling things constantly on every tick of the game.
 *
 * @author Tecknix Software
 */
public class TMTickEvent extends TMEvent {

}
```

### Methods:

Methods should have a header including the purpose of the method, the author of the method and the method's parameters.

```java
/**  
 * Post an event to the API. * * @param event Passes through the event to post.  
 * @author Tecknix Software.  
 */
 public static void post(final TMEvent event) {  
	  //Gets the content object from the map if the key matches that of this events class.  
	  final ArrayList<EventContent> contents = EVENT_REGISTRY.get(event.getClass());  
	  if (contents != null) {  
		  for (final EventContent content : contents) {  
				  event.invoke(content);  
		  } 
	  } 
 }


