# WelcomeReward Plugin for Minecraft

The WelcomeReward plugin enhances the Minecraft multiplayer experience by incentivizing players to welcome new members to the server. Each time a player sends a specific welcome message in chat, they are rewarded with in-game currency, promoting a friendly and engaging community atmosphere.

## Features

- **Automated Rewards**: Automatically rewards players with in-game currency for welcoming new players.
- **Configurable**: Easily customizable reward amount, welcome message, time threshold, and more.
- **Color-coded Messages**: Supports Minecraft color codes in messages for vibrant and attractive in-game feedback.

## Requirements

- **Minecraft Server**: This plugin is designed to work on Minecraft servers.
- **Vault Plugin**: Requires the Vault plugin for handling economy transactions.
- **Economy Plugin**: An economy plugin like EssentialsX that integrates with Vault.

## Installation

1. Download the `WelcomeReward.jar` file from the Releases section.
2. Place the `WelcomeReward.jar` file into your `plugins` folder in your Minecraft server's directory.
3. Restart the server.
4. Ensure the Vault plugin and an economy plugin are correctly set up and operational.

## Configuration

After the first run, the `config.yml` file will be created in the plugin's data folder. You can edit this file to customize the plugin's behavior. Here's an overview of the configurable options:

```yaml
welcome:
  message: "welcome"             # The message players must send to trigger the reward.
  timeThreshold: 15000           # Time in milliseconds within which the welcome message must be sent after a player joins.
  rewardAmount: 1000.0           # The amount of in-game currency to reward.
  rewardMessage: "&aWow! You welcomed a new player! You are so nice, here is $%amount% Shekels!"  # The message to send to the player who welcomed someone.
