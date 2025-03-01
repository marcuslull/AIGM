# AI GM Development Notes

## Initial Goal

To explore the feasibility of using AI, specifically large language models (LLMs), to function as a Game Master in a role-playing game (RPG) setting. [cite: 1]

## Challenges

*   Maintaining long-term narrative coherence: AI struggles to keep track of complex plots, character arcs, and world changes over extended gameplay (e.g., 500 hours). [cite: 2]
*   Dynamic adaptation and improvisation: While AI can adapt to some player choices, truly emergent gameplay that significantly alters the story is difficult. [cite: 3]
*   Creative problem-solving: AI can struggle with unexpected situations that require improvisation and creative solutions. [cite: 4]
*   Social dynamics and player engagement: Managing player interactions, fostering social dynamics, and handling interpersonal conflicts are challenging for AI. [cite: 5]

## Assumptions and Constraints (to increase feasibility)

*   Pre-made adventure: Using a pre-written adventure with defined boundaries. [cite: 6]
*   Limited scope: Focusing on a shorter gameplay duration (around 50 hours). [cite: 7]
*   Key point memory: The AI would only need to remember crucial information from previous sessions. [cite: 8]
*   Overarching narratives provided: The main plot points and narrative arcs would be pre-defined. [cite: 9]

## Proposed Solutions and Strategies

*   Hybrid approach: Combining human GM oversight with AI assistance for rules, mechanics, and NPC interactions. [cite: 10]
*   Structured narrative design: Breaking the campaign into smaller modules, defining key story beats, and using knowledge graphs to represent the game world. [cite: 11]
*   Enhanced AI capabilities: Improving long-context models, implementing memory mechanisms, and fine-tuning models on RPG data. [cite: 12]
*   Player-driven narrative: Empowering player choices and encouraging collaborative storytelling. [cite: 13]
*   Distributed architecture: Using different models or applications for specific tasks (social interactions, combat, character management, etc.). [cite: 14]
*   Virtual tabletop (VTT) integration: Leveraging VTT platforms with APIs for visual representation, interactive elements, and pre-built assets. [cite: 15]
* **Vector Database Retrieval**: Incorporating the use of a vector database to retrieve relevant information from past game sessions, feedback, and world knowledge. This is intended to reduce prompt length and increase performance.
* **Fine-tuning for Internalized Knowledge**: Using feedback and game data to fine-tune the AI models over time. The goal is to reduce the need to add information to system prompts, and improve general performance.

## Potential Issues and Mitigations

*   Social dynamics: Remains a significant challenge. [cite: 16] Mitigations include scripting key social encounters, simplifying social models, and using rule-based social mechanics. [cite: 17]
*   100% AI GM replacement: While challenging, it's closer to feasibility with the defined constraints. [cite: 18] Focus on structured adventures, limited scope, and continuous improvement. [cite: 19]
* **Deferral Object Overhead**:  The system of using deferral objects for all AI communication has the potential to introduce significant overhead. This will need to be monitored and addressed as the system grows.
* **Handoff and Continuity Overlap**: The roles of the Handoff and Continuity models have some overlap, specifically around evaluating communication. This relationship will need to be monitored, and refined to ensure they are complimentary and not conflicting.

## Technology and Platforms

*   Language models: GPT-4, Gemini, Claude are potential candidates, with Gemini currently appearing most promising due to its multimodal capabilities. [cite: 19]
*   Development stack: Spring Boot with Spring AI is a suitable framework for building the service, given your Java preference. [cite: 20]
*   Cloud platform: Google Cloud's Vertex AI is likely the best platform for accessing and customizing Gemini. [cite: 21]
*   VTT platforms: Foundry VTT and Roll20 are potential candidates for integration due to their APIs and community support. [cite: 22]
* **Vector Database:** Potential vector databases include Chroma, Pinecone or a custom solution using Postgres.

## Distributed AI Architecture

The system will utilize a distributed architecture with specialized AIs handling different aspects of the game, such as:

*   Social AI: Manages NPC interactions, dialogue, and social encounters. [cite: 23]
*   Combat AI: Handles combat encounters, including tactics, strategy, and resolution of actions. [cite: 24]
*   Narrative AI: Manages the overall narrative flow, story events, and world-building. [cite: 25]
*   History AI: Maintains a record of past events, lore, and character backgrounds. [cite: 26]
*   Master AI: Oversees the entire system, handles complex or unexpected situations, and ensures consistency across all AIs. [cite: 27]
*   **Continuity AI:** Monitors and evaluates all game communication, both deferral objects and player/GM text, to ensure narrative consistency and quality.
*   **Handoff AI:** Analyzes all deferral objects to provide feedback and suggest improvements to inter-AI communication efficiency and effectiveness.

## AI Communication

AIs will communicate with each other through various methods, including:

*   API calls: For requesting information or triggering actions. [cite: 28]
*   Message queue: For asynchronous communication and loose coupling. [cite: 29]
*   Shared memory: For high-bandwidth or real-time data exchange. [cite: 30]
*   Language-based communication: Using natural language or a custom language for inter-AI communication. [cite: 31]
* **Deferral Objects**: All AI-to-AI communication is currently handled by Deferral Objects.

## State Package Handoff

When transitioning between AIs, a "state package" will be passed along, containing relevant information about the current game state, player characters, and recent events. [cite: 32] This ensures a smooth and consistent experience for the players. [cite: 33]

## AI vs. Tool Services

The AI will focus on core aspects of the game requiring intelligence, creativity, and adaptability, while external tools will handle more specific tasks. [cite: 34] This includes:

*   AI-powered services: Narrative generation, NPC interaction, world simulation, decision-making. [cite: 35]
*   Tool-based services: Dice rolling, character management, VTT integration, content generation. [cite: 36]

## Discord Integration

Integrate with Discord (or a similar platform) to enable:

*   Private channels for players to communicate with the AI. [cite: 37]
*   Character-specific channels for immersive roleplaying. [cite: 38]
    *   Secret actions, in-character questions, and meta-gaming discussions.
*   Tactical Combat AI

## Tactical Combat AI

The combat AI will be designed as an expert tactician, capable of:

*   Utilizing tactical profiles for different creatures. [cite: 39]
*   Adapting its strategies based on the situation and creatures involved. [cite: 40]
*   Dynamically adjusting difficulty based on player performance. [cite: 41]
*   Learning and improving its tactics over time.

## Player Action Handling

Key considerations for handling player actions include:

*   Action interpretation (NLU). [cite: 42]
*   Ambiguity and clarification. [cite: 43]
*   Action validation against game rules. [cite: 43]
*   Consequences and feedback. [cite: 43]
*   Handling unexpected or creative actions. [cite: 44]

## Hierarchical Call Structure

Implement a hierarchical call structure for handling unexpected player actions:

*   Local AI attempts to handle the action. [cite: 44]
*   Escalate to Narrative AI if necessary. [cite: 45]
*   Final call to Master AI for complex or game-altering decisions.

## Player Input and AI Interjections

This section details discussion around how players will communicate with and receive communications from the AI GM. [cite: 46]

### Key Goals

*   Intuitive and seamless player input using natural language. [cite: 47]
*   Multi-modal input including voice and potentially gestures. [cite: 48]
*   Integration with VTT platforms using APIs. [cite: 49]
*   Passive listening mode for dynamic group conversations. [cite: 50]
*   Addressing challenges of multi-player intent recognition and AI interjections. [cite: 50]
*   Ethical considerations for relationship mapping and bias prevention. [cite: 51]
*   Playtesting and player feedback for iterative improvement. [cite: 52]

### Technical Details

*   Predefined player-to-connection mapping for speaker identification. [cite: 53]
*   Training data and contextual clues for action classification. [cite: 54]
*   Dynamic relationship scoring and potential safeguards. [cite: 55]
*   Prioritization system and adjustable response delay for AI interjections. [cite: 55]

### Open Questions

*   Advanced voice recognition for speaker identification. [cite: 56]
*   Implementation of relationship mapping and associated ethical considerations. [cite: 57]
*   Fine-tuning AI interjections based on playtesting and player feedback. [cite: 58]
*   Distinguishing between player and character voices. [cite: 59]

## AI-to-AI Communication Protocol

*   All AI-to-AI communication must be conducted through **DeferralObjects**. [cite: 62]
*   DeferralObjects are JSON objects with the following structure:

    ```json
    {
      "deferralId": "UUID",
      "deferralType": "INFORMATION_REQUEST", 
      "sourceAI": "Your Role",
      "targetAI": "Target AI Role",
      "priority": "HIGH",
      "data": {},
      "context": {},
      "relatedDeferralIds": [], 
      "confidenceScore": 0.8
    }
    ```

*   Deferral requests should be made in their own response. [cite: 62]
*   The content of DeferralObjects is not shared with the players. [cite: 62]
*   When sending a DeferralObject, you must wait for a response before proceeding. [cite: 62]

### Deferral Types

*   `INFORMATION_REQUEST`
*   `ACTION_VALIDATION`
*   `EVENT_TRIGGER`
*   `DIALOGUE_GENERATION`
*   `COMBAT_RESOLUTION`
*   `WORLD_STATE_UPDATE`
*   `DIALOGUE_FEEDBACK`
*   `FEEDBACK_REPORT`
*   `OTHER` (Use this type when none of the above fit. Include a description in the `data` field.)

### Priority Levels

*   `HIGH`
*   `MEDIUM`
*   `LOW`

### General Rules for AI models

*   **Breaking the 4th wall is strictly prohibited.** Players should not be aware of the AI-to-AI communication. [cite: 61, 62]
*   Maintain character integrity and consistency. [cite: 62]
*   Adhere to the rules and mechanics of the game system. [cite: 62]
*   Strive to create a fun and engaging experience for the players. [cite: 62]

## HandoffEval Observations and Recommendations

*   **Versioning for World State Updates:** Add a `version` field to `WORLD_STATE_UPDATE` data to track changes.
*   **Reason for Dialogue Generation:** Include a `reason` field in `DIALOGUE_GENERATION` requests to provide context.
*   **Urgency for Plot Hooks:** Add an `urgency` or `importance` field to each plot hook in `INFORMATION_REQUEST_RESPONSE`.
*   **Feedback on Dialogue:** Implement a `DIALOGUE_FEEDBACK` deferral type for feedback on generated dialogue.
*   **Standardize Context Objects:** Ensure consistency in `context` objects across all deferral types. Consider creating a shared schema or data model for context.
*   **Automated Performance Metrics:** Implement automated metrics to track deferral frequency and duration.
*   **Consider a Deferral Management Tool:** Develop a tool for managing and visualizing deferrals as the system grows.
*   **Refine Deferral Triggers:** Analyze and optimize AI responsibilities to potentially reduce deferrals.
*   **Error Handling and Fallback Mechanisms:** Implement robust error handling and fallback mechanisms for deferral failures.

## Overall

While a perfect, fully autonomous AI GM for a long-term campaign is still a stretch, a more structured, rule-focused game with a shorter duration and pre-defined narrative is much more achievable. [cite: 68] By combining AI with human oversight, structured design, and integration with existing tools, you can create a compelling and engaging AI-powered RPG experience. [cite: 69]

## Current Focus

* **Hybrid Approach:** Begin focusing on implementing a vector database to store embeddings of game events, lore, and feedback. This will be tested by allowing the History AI to make queries against the vector database.
* **Data Collection**: Continue collecting data, using handoff and continuity, for future training.
* **Fine-tuning:** Future plans include leveraging the data collected to fine-tune the AI Models.