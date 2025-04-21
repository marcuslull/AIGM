# ABOUT AI GAME MASTER

**A note on AI usage for development**:  
This is a passion project of which I intend on having fun with, and I enjoy coding myself! As such, AI is used in an "assistant" capacity for mundane tasks or affirmations but never as the lead developer.

## Initial Goal

To explore the feasibility of using AI to function as a Game Master in a role-playing game setting.

## Challenges

* **Maintaining long-term narrative coherence**: AI struggles to keep track of complex plots, character arcs, and world changes over extended gameplay (500+ hours).
* **Dynamic adaptation and improvisation**: While AI can adapt to some player choices, truly emergent gameplay that significantly alters the story is difficult.
* **Creative problem-solving**: AI can struggle with unexpected situations that require improvisation and creative solutions.
* **Social dynamics and player engagement**: Managing player interactions, fostering social dynamics, and handling interpersonal conflicts are challenging for AI.

## Assumptions and Constraints

* **Pre-made adventure**: Using a pre-written adventure with defined boundaries.
* **Limited scope**: Currently focus on a shorter gameplay duration (around 50 hours).

## Proposed Solutions and Strategies

* **Hybrid approach**: Combining human co-pilot with AI pilot for narrative, rules, mechanics, and NPC interactions.
* **Structured narrative design**: Breaking the campaign into smaller modules, defining key story beats, and using knowledge graphs to represent the game world.
* **Enhanced AI capabilities**: Improving long-context models, implementing memory mechanisms, databases and tools.
* **Player-driven narrative**: Empowering player choices and encouraging collaborative storytelling.
* **Model Specialization**: Using different models or applications for specific tasks (social interactions, combat, character management, etc.).
* **System Prompts**: Tuned prompts to establish role, responsibility, scope for each model.
* **Chat Memory**: Incorporating a chat memory advisor for session memory.
* **Vector Database Retrieval**: Incorporating the use of a vector database to retrieve relevant information from published works.
* **SQL Database Retrieval**: Incorporating the use of a SQL database to retrieve relevant information from tabular data such as character stats, items, spells, feats, etc...
* **Tools**: Providing tools for players and AI models such as dice roller, treasure generator, random encounter generator, etc...
* **Fine-tuning for Internalized Knowledge**: Using feedback and game data to fine-tune the AI models over time. The goal is to reduce the need to add information to system prompts, and improve general performance.
* **Virtual tabletop (VTT)**: Leveraging VTT platforms with APIs for visual representation, interactive elements, and pre-built assets.

## Technology and Platforms

* **Language models**: Gemini currently appearing most promising due to its multimodal capabilities and low cost.
* **Development stack**: Java, Spring Boot with Spring AI is a suitable framework for building the service. It is still under development but looks to go production soon and will continue aggressive development.
* **Cloud platform**: Google Cloud's Vertex AI is likely the best platform for accessing and customizing Gemini.
* **VTT platforms**: Foundry VTT and Roll20 are potential candidates for integration due to their APIs and community support.
* **Database**: Postgres appears to be the frontrunner with both vector and SQL schemas to offer.
* **Tooling**: Custom tool design with access via structured AI response
* **Development Deployment**: Start with Spring Docker Compose adapt for expanding needs and availability.
* **Discord Integration**: Optional depending on VTT integrations. Integrate with Discord or similar platform to enable party, player-to-player, direct message and multi-modal communication to/from AI group.

## Distributed AI Architecture

The system will utilize distributed, specialized AIs handling different aspects of the game. Given a finite context window and the overall goal of 500+ hours of gameplay to a campaign it will be necessary to divide up responsibility between AI models. I'm sure this will be ever-changing but here are some possibilities:

* **Social AI**: Manages NPC interactions, dialogue, and social encounters.
* **Combat AI**: Handles combat encounters, including tactics, strategy, and resolution of actions. 
* **Narrative AI**: Manages the overall narrative flow, story events, and world-building.
* **History AI**: Maintains a record of past events, lore, and character backgrounds.
* **Master AI**: Oversees the entire system, handles complex or unexpected situations, and ensures consistency across all AIs.
* **Continuity AI**: Monitors and evaluates all game communication, both deferral objects and player/GM text, to ensure narrative consistency and quality.
* **Data retrieval or Tool AI**: Utilizes resources such as vector or SQL databases, exposed tools, Inter-AI communication to gather data.

## AI Communication

AIs will communicate with each other through various methods and tools, including:

* **Communication Protocol**: A communication wrapper packet that contains the various forms of communication or data access channels.
* Shared memory: For high-bandwidth or real-time data exchange.
* Language-based communication: Using natural language inter-AI communication.

## Tactical Combat AI

The combat AI will be designed as an expert tactician, capable of:

* Utilizing tactical profiles for different creatures.
* Adapting its strategies based on the situation and creatures involved.
* Dynamically adjusting difficulty based on player performance, experience and preference.
* Learning and improving its tactics over time.

## Goals

* Intuitive and seamless player input using natural language.
* Multi-modal input including text, voice, image
* Integration with VTT platforms using APIs.
* Passive listening mode for dynamic group conversations.
* Addressing challenges of multi-player intent recognition and AI interjections.
* Playtesting and player feedback for iterative improvement.

## Overall

While a perfect, fully autonomous AI GM for a long-term campaign is still a stretch, a more structured, rule-focused game with a shorter duration and pre-defined narrative is much more achievable. By combining AI with human oversight, structured design, and integration with existing tools, you can create a compelling and engaging AI-powered RPG experience.

## Current Focus

* Data - Vector, SQL DB queries
* Tooling - Dice rolling, generators, etc...
* Refactoring pass for better design, maintainability
* Testing