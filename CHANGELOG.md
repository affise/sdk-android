# Changelog

## [1.6.48] - 2024-11-21

### Added

- New module `RuStore`.

## [1.6.47] - 2024-10-21

### Fixed

- Fix `Internal` library for crossplatform.

## [1.6.46] - 2024-10-21

### Fixed

- Fix CPU overhead for activity listeners.

### Removed

- Api `setEnabledMetrics`.
- Api `setAutoCatchingTypes`.

## [1.6.45] - 2024-10-03

### Fixed

- Fix `Affise.Module.getStatus` timing retry.

## [1.6.44] - 2024-09-04

### Fixed

- Fix `Gaid` in `Advertising` module.

## [1.6.43] - 2024-09-03

### Fixed

- Module `Subscription`.

### Changed

- Update `internal` library for crossplatform.

## [1.6.42] - 2024-08-19

### Changed

- Api `Affise.getReferrer` to `Affise.getReferrerUrl`.
- Api `Affise.getReferrerValue` to `Affise.getReferrerUrlValue`.

## [1.6.41] - 2024-08-14

### Added

- New internal events.

## [1.6.40] - 2024-07-29

### Fixed

- Api `Affise.Module.getStatus`.

## [1.6.39] - 2024-07-22

### Added

- Modules compatibility check.

## [1.6.38] - 2024-07-03

### Changed

- Update `kotlin` version `1.8.22`.

### Fixed

- AGP error for `crossplatform`.
- Event timestamp field for `crossplatform`.

## [1.6.37] - 2024-06-24

### Added

- New module `Link`.

### Changed

- Update api `Affise.getStatus` moved to `Affise.Module.getStatus`.
- Update api `Affise.moduleStart` moved to `Affise.Module.moduleStart`.
- Update api `Affise.getModulesInstalled` moved to `Affise.Module.getModulesInstalled`.

## [1.6.36] - 2024-06-19

### Changed

- Update `registerDeeplinkCallback` change uri to convenient values.
- Update `registerDeeplinkCallback` return removed.

## [1.6.35] - 2024-06-13

### Changed

- Update `Demo app`.
- Update modules extensions.

## [1.6.34] - 2024-06-03

### Changed

- Update `internal` library for `crossplatform`.

## [1.6.33] - 2024-05-29

### Removed

- Result for failed `sendNow`.

### Changed

- Update `kotlin` version `1.6`.
- Update each modules for separate `kotlin` version. 
- Update `internal` library for `crossplatform`.
- Update `gradle-wrapper` version `7.5`.

## [1.6.32] - 2024-04-26

### Fixed

- CI/CD.

## [1.6.31] - 2024-04-26

### Added

- New module `AndroidId`.

### Changed

- Fix typos in `Subscription` module.

## [1.6.30] - 2024-04-19

### Added

- Event api `sendNow`.

### Removed

- Api `sendEvents`.

### Changed

- Fix ExecutorServiceProvider.

## [1.6.29] - 2024-04-16

### Added

- New module `Subscription`.
- New event `FailedPurchaseEvent`.
- New values in `PredefinedString`.

### Changed

- Improve `AffiseModuleManager`
- Update gradle for demo app.

[1.6.48]: https://github.com/affise/sdk-android/compare/v1.6.47...v1.6.48
[1.6.47]: https://github.com/affise/sdk-android/compare/v1.6.46...v1.6.47
[1.6.46]: https://github.com/affise/sdk-android/compare/v1.6.45...v1.6.46
[1.6.45]: https://github.com/affise/sdk-android/compare/v1.6.44...v1.6.45
[1.6.44]: https://github.com/affise/sdk-android/compare/v1.6.43...v1.6.44
[1.6.43]: https://github.com/affise/sdk-android/compare/v1.6.42...v1.6.43
[1.6.42]: https://github.com/affise/sdk-android/compare/v1.6.41...v1.6.42
[1.6.41]: https://github.com/affise/sdk-android/compare/v1.6.40...v1.6.41
[1.6.40]: https://github.com/affise/sdk-android/compare/v1.6.39...v1.6.40
[1.6.39]: https://github.com/affise/sdk-android/compare/v1.6.38...v1.6.39
[1.6.38]: https://github.com/affise/sdk-android/compare/v1.6.37...v1.6.38
[1.6.37]: https://github.com/affise/sdk-android/compare/v1.6.36...v1.6.37
[1.6.36]: https://github.com/affise/sdk-android/compare/v1.6.35...v1.6.36
[1.6.35]: https://github.com/affise/sdk-android/compare/v1.6.34...v1.6.35
[1.6.34]: https://github.com/affise/sdk-android/compare/v1.6.33...v1.6.34
[1.6.33]: https://github.com/affise/sdk-android/compare/v1.6.32...v1.6.33
[1.6.32]: https://github.com/affise/sdk-android/compare/v1.6.31...v1.6.32
[1.6.31]: https://github.com/affise/sdk-android/compare/v1.6.30...v1.6.31
[1.6.30]: https://github.com/affise/sdk-android/compare/v1.6.29...v1.6.30
[1.6.29]: https://github.com/affise/sdk-android/compare/v1.6.28...v1.6.29
